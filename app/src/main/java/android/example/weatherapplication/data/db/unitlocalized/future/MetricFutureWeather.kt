package android.example.weatherapplication.data.db.unitlocalized.future

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class MetricFutureWeather(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "avgtempC")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "maxtempC")
    override val maxTemperature: Double
): UnitSpecificSimpleFutureWeatherEntry
