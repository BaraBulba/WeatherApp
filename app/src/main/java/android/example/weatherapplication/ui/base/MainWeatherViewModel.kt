package android.example.weatherapplication.ui.base

import android.example.weatherapplication.data.provider.UnitProvider
import android.example.weatherapplication.data.repository.ForecastRepository
import android.example.weatherapplication.internal.UnitSystem
import android.example.weatherapplication.internal.lazyDeferred
import androidx.lifecycle.ViewModel

abstract class MainWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
): ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}