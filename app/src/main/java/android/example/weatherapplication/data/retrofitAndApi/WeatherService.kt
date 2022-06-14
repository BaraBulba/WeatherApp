package android.example.weatherapplication.data.retrofitAndApi


import android.example.weatherapplication.data.network.ConnectivityInterceptor
import android.example.weatherapplication.data.network.ConnectivityInterceptorImpl
import android.example.weatherapplication.data.network.response.CurrentWeatherResponse
import android.example.weatherapplication.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "c790871d4d524cd89c3141756221204"

interface WeatherService {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q")location: String,
        @Query("lang")languageCode: String
    ): Deferred<CurrentWeatherResponse>

    @GET("forecast.json")
    fun getFutureWeather(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "ru"
    ):Deferred<FutureWeatherResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }
}