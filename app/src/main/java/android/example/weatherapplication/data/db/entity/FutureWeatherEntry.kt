package android.example.weatherapplication.data.db.entity


import android.example.weatherapplication.data.network.response.Hour
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
    val date: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @Embedded
    val day: Day,
//    val hour: List<Hour>
)