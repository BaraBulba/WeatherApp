package android.example.weatherapplication.data.provider

import android.example.weatherapplication.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferedLocationString(): String
}