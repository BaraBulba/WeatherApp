package android.example.weatherapplication.data.db

import android.content.Context
import android.example.weatherapplication.data.db.entity.CurrentWeatherEntry
import android.example.weatherapplication.data.db.entity.FutureWeatherEntry
import android.example.weatherapplication.data.db.entity.WeatherLocation
import androidx.room.*

@Database(
    entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase: RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun futureWeatherDao(): FutureWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object{
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ForecastDatabase::class.java, "futureWeatherEntries.db")
                .build()

    }
}