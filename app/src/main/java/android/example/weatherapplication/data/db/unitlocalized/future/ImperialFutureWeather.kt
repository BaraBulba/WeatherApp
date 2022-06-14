package android.example.weatherapplication.data.db.unitlocalized.future

import android.example.weatherapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeather
import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class ImperialFutureWeather (
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "avgtempF")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "maxtempF")
    override val maxTemperature: Double

): UnitSpecificSimpleFutureWeatherEntry
