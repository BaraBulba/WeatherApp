package android.example.weatherapplication.ui.future.list

import android.example.weatherapplication.data.provider.UnitProvider
import android.example.weatherapplication.data.repository.ForecastRepository
import android.example.weatherapplication.internal.lazyDeferred
import android.example.weatherapplication.ui.base.MainWeatherViewModel
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDate

class ForecastViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
): MainWeatherViewModel(forecastRepository, unitProvider) {
    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}