package android.example.weatherapplication.ui.future.list

import android.example.weatherapplication.R
import android.example.weatherapplication.data.db.FutureWeatherDao
import android.example.weatherapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import android.example.weatherapplication.ui.base.ScopedFragment
import android.example.weatherapplication.ui.current.CurrentWeatherModelFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.erased.instance
import org.threeten.bp.LocalDate

class ForecastFragment : ScopedFragment(), KodeinAware {


    override val kodein by kodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.rgb(123,31,162)))
        (activity as AppCompatActivity).supportActionBar?.title = "7 Дней"
        (activity as AppCompatActivity).supportActionBar?.subtitle = null
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ForecastViewModel::class.java)
        bindUI()
        Log.d("TAG", "onActivityCreated")

    }

    private fun bindUI () = launch(Dispatchers.Main){
        val futureWeatherEntries = viewModel.weatherEntries.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeatherEntries.observe(viewLifecycleOwner, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer

            initRecyclerView(weatherEntries.toFutureWeatherItems())

        })

    }

    private fun updateLocation(location: String){
        (activity as AppCompatActivity).supportActionBar?.title = location
    }

    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toFutureWeatherItems(): List<ForecastWeatherItem>{
        return this.map {
            ForecastWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<ForecastWeatherItem>){
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        forecastRecycler.apply {
            layoutManager = LinearLayoutManager(this@ForecastFragment.context)
            adapter = groupAdapter
        }
    }

    private fun updateDate(date: String) {
        tv_today_date.text = "$date"
    }
}

