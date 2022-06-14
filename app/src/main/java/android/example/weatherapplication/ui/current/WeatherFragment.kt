package android.example.weatherapplication.ui.current

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.weatherapplication.R
import android.example.weatherapplication.ui.base.ScopedFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.get
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.erased.instance


class WeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()

    private val viewModelFactory: CurrentWeatherModelFactory by instance()


    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(WeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer {location ->
            if (location == null) return@Observer
            updateLocation(location.name + "," + location.country)
        })

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateTemperature(it.temperature)
            updateCondition(it.conditionText)
            updateClouds(it.cloud)
            updateHumidity(it.humidityText)
            updateWind(it.windSpeed)
        })
    }
    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }
    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
    }

    private fun updateTemperature(temperature: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        temperatureInfo.text = "$temperature$unitAbbreviation"
    }
    private fun updateCondition(condition: String){
        weatherTextInfo.text = condition
    }
    private fun updateClouds(cloud: Int){
        clouds_info.text = "$cloud%"
    }
    private fun updateHumidity(humidity: Int){
        humidityInfo.text = "$humidity%"
    }
    private fun updateWind(wind: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation(" kph", " mph")
        windInfo.text = "$wind$unitAbbreviation"
    }

}