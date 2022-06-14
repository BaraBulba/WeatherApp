package android.example.weatherapplication.data.repository

import android.example.weatherapplication.data.db.CurrentWeatherDao
import android.example.weatherapplication.data.db.FutureWeatherDao
import android.example.weatherapplication.data.db.WeatherLocationDao
import android.example.weatherapplication.data.db.entity.WeatherLocation
import android.example.weatherapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeather
import android.example.weatherapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import android.example.weatherapplication.data.network.FORECAST_DAYS_COUNT
import android.example.weatherapplication.data.network.WeatherNetworkDataSource
import android.example.weatherapplication.data.network.response.CurrentWeatherResponse
import android.example.weatherapplication.data.network.response.FutureWeatherResponse
import android.example.weatherapplication.data.provider.LocationProvider
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

        override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather> {
            return withContext(Dispatchers.IO) {
                initWeatherData()
                return@withContext if (metric) currentWeatherDao.getWeatherMetric()
                else currentWeatherDao.getWeatherImperial()
            }
        }


        override suspend fun getFutureWeatherList(
            startDate: LocalDate,
            metric: Boolean
        ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
            return withContext(Dispatchers.IO) {
                initWeatherData()
                return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastMetric(
                    startDate
                )
                else futureWeatherDao.getSimpleWeatherForecastImperial(startDate)
            }
        }

        override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
            return withContext(Dispatchers.IO) {
                return@withContext weatherLocationDao.getLocation()

            }
        }

        private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
            GlobalScope.launch(Dispatchers.IO) {
                currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
                weatherLocationDao.upsert(fetchedWeather.location)
            }
        }

        private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
            fun deleteOldForecastData() {
                val today = LocalDate.now()
                futureWeatherDao.deleteOldEntries(today)
            }
            GlobalScope.launch(Dispatchers.IO) {
                val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
                futureWeatherDao.insert(futureWeatherList)
                weatherLocationDao.upsert(fetchedWeather.location)
            }
        }


    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }
        if (isFetchedCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
        if (isFetchedFuturNeeded())
            fetchFutureWeather()
    }
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferedLocationString(),
            Locale.getDefault().language
        )
    }
    private suspend fun fetchFutureWeather(){
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferedLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchedCurrentNeeded(lastFetchTime: org.threeten.bp.ZonedDateTime): Boolean{
        val thirtyMinutesAgo = org.threeten.bp.ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
    private fun isFetchedFuturNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }
}