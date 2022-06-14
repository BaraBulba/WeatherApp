package android.example.weatherapplication.ui.current

import android.example.weatherapplication.data.provider.UnitProvider
import android.example.weatherapplication.data.repository.ForecastRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrentWeatherModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(forecastRepository, unitProvider) as T
    }
}