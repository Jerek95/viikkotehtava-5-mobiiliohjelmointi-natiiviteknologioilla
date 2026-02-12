package com.example.viikko5.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit
import kotlin.getValue

data class Weather(
    val main: String,
    val description: String,
    val icon: String,
    val temp: Int
)

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?
)

interface ApiService {
    @GET("weather")
    suspend fun getWeather(): List<Weather>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather?"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}