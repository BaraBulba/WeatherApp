package android.example.weatherapplication.data.network.response

import android.example.weatherapplication.data.db.entity.CurrentWeatherEntry
import android.example.weatherapplication.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation
)