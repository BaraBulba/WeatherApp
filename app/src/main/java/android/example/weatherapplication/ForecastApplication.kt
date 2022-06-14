package android.example.weatherapplication

import android.app.Application
import android.content.Context
import android.example.weatherapplication.data.db.ForecastDatabase
import android.example.weatherapplication.data.network.ConnectivityInterceptor
import android.example.weatherapplication.data.network.ConnectivityInterceptorImpl
import android.example.weatherapplication.data.network.WeatherNetworkDataSource
import android.example.weatherapplication.data.network.WeatherNetworkDataSourceImpl
import android.example.weatherapplication.data.provider.LocationProvider
import android.example.weatherapplication.data.provider.LocationProviderImpl
import android.example.weatherapplication.data.provider.UnitProvider
import android.example.weatherapplication.data.provider.UnitProviderImpl
import android.example.weatherapplication.data.repository.ForecastRepository
import android.example.weatherapplication.data.repository.ForecastRepositoryImpl
import android.example.weatherapplication.data.retrofitAndApi.WeatherService
import android.example.weatherapplication.ui.current.CurrentWeatherModelFactory
import android.example.weatherapplication.ui.current.WeatherViewModel
import android.example.weatherapplication.ui.future.list.FutureListWeatherViewModelFactory
import android.location.Location
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class ForecastApplication: Application(), KodeinAware {
    override val kodein =  Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(),
            instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preference, false)
    }
}