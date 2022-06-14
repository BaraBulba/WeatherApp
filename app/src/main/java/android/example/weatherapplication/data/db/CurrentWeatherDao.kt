package android.example.weatherapplication.data.db

import android.example.weatherapplication.data.db.entity.CURRENT_WEATHER_ID
import android.example.weatherapplication.data.db.entity.CurrentWeatherEntry
import android.example.weatherapplication.data.db.unitlocalized.current.ImperialCurrentWeather
import android.example.weatherapplication.data.db.unitlocalized.current.MetricCurrentWeather
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id =  $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeather>

    @Query("select * from current_weather where id =  $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeather>
}