package android.example.weatherapplication.ui.future.list

import android.example.weatherapplication.R
import android.example.weatherapplication.data.db.unitlocalized.future.MetricFutureWeather
import android.example.weatherapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ForecastWeatherItem(
    val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
): com.xwray.groupie.kotlinandroidextensions.Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            textView_short_info.text = weatherEntry.conditionText
            updateDate()
            updateAvgTemperature()
            updateMaxTemperature()
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun GroupieViewHolder.updateDate(){
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dtFormatter)
    }

    private fun GroupieViewHolder.updateAvgTemperature(){
        val unitAbbreviation = if (weatherEntry is MetricFutureWeather) "°C"
        else "°F"
        textView_avg_temperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"
    }

    private fun GroupieViewHolder.updateMaxTemperature(){
        val unitAbbreviation = if (weatherEntry is MetricFutureWeather) "°C"
        else "°F"
        textView_max_temperature.text = "${weatherEntry.maxTemperature}$unitAbbreviation"
    }

    private fun GroupieViewHolder.updateConditionImage(){
        Glide.with(this.containerView)
            .load("http:" + weatherEntry.conditionIconUrl)
            .into(imageView_condition_icon)
    }
}