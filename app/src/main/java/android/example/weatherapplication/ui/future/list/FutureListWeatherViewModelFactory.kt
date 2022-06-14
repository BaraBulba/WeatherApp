package android.example.weatherapplication.ui.future.list

import android.example.weatherapplication.data.provider.UnitProvider
import android.example.weatherapplication.data.repository.ForecastRepository
import android.example.weatherapplication.ui.current.WeatherViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FutureListWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForecastViewModel(forecastRepository, unitProvider) as T
    }
}