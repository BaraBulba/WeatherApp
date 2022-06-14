package android.example.weatherapplication.data.repository

import android.example.weatherapplication.data.db.entity.WeatherLocation
import android.example.weatherapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeather
import android.example.weatherapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import androidx.lifecycle.LiveData
import org.threeten.bp.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean)
    : LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}