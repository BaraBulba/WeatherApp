package android.example.weatherapplication.data.db.unitlocalized.future

import org.threeten.bp.LocalDate

interface UnitSpecificSimpleFutureWeatherEntry {
    val maxTemperature: Double
    val date: LocalDate
    val conditionText: String
    val conditionIconUrl: String
    val avgTemperature: Double

}