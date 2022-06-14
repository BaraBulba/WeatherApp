package android.example.weatherapplication.data.db.unitlocalized.current

import androidx.room.ColumnInfo

data class ImperialCurrentWeather (
    @ColumnInfo(name = "tempF")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "windMph")
    override val windSpeed: Double,
    @ColumnInfo(name = "humidity")
    override val humidityText: Int,
    @ColumnInfo(name = "cloud")
    override val cloud: Int

): UnitSpecificCurrentWeather
