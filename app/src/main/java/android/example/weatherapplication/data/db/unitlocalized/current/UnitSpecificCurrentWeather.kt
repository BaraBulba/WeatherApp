package android.example.weatherapplication.data.db.unitlocalized.current

interface UnitSpecificCurrentWeather {
    val temperature: Double
    val conditionText: String
    val windSpeed: Double
    val humidityText: Int
    val cloud: Int
}