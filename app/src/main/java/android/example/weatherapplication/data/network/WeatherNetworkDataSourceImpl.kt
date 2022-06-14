package android.example.weatherapplication.data.network

import android.example.weatherapplication.data.network.response.CurrentWeatherResponse
import android.example.weatherapplication.data.network.response.FutureWeatherResponse
import android.example.weatherapplication.data.retrofitAndApi.WeatherService
import android.example.weatherapplication.internal.NoConnectivityException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

const val FORECAST_DAYS_COUNT = 7
class WeatherNetworkDataSourceImpl(
    private val weatherService: WeatherService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = weatherService
                .getCurrentWeather(location, languageCode)
                .await()
                _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(location: String, languageCode: String) {
        try {
            val fetchedFutureWeather = weatherService
                .getFutureWeather(location, FORECAST_DAYS_COUNT, languageCode)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }
}