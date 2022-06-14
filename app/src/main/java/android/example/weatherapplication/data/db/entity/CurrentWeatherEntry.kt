package android.example.weatherapplication.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val cloud: Int,
    @Embedded(prefix = "condition_")
    val condition: Condition,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName( "wind_mph")
    val windMph: Double

)
{   @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}