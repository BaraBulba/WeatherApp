package android.example.weatherapplication.ui.current

import android.example.weatherapplication.data.provider.UnitProvider
import android.example.weatherapplication.data.repository.ForecastRepository
import android.example.weatherapplication.internal.UnitSystem
import android.example.weatherapplication.internal.lazyDeferred
import android.example.weatherapplication.ui.base.MainWeatherViewModel
import androidx.lifecycle.ViewModel

class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : MainWeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }

}