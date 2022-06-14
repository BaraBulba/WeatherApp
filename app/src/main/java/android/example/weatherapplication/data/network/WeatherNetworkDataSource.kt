package android.example.weatherapplication.data.network

import android.example.weatherapplication.data.db.CurrentWeatherDao
import android.example.weatherapplication.data.network.response.CurrentWeatherResponse
import android.example.weatherapplication.data.network.response.FutureWeatherResponse
import androidx.lifecycle.LiveData

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    )
}