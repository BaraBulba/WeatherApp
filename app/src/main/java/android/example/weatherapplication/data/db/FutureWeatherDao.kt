package android.example.weatherapplication.data.db

import android.example.weatherapplication.data.db.entity.FutureWeatherEntry
import android.example.weatherapplication.data.db.unitlocalized.future.ImperialFutureWeather
import android.example.weatherapplication.data.db.unitlocalized.future.MetricFutureWeather
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.threeten.bp.LocalDate
@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastMetric(startDate: LocalDate): LiveData<List<MetricFutureWeather>>

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastImperial(startDate: LocalDate): LiveData<List<ImperialFutureWeather>>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)

}