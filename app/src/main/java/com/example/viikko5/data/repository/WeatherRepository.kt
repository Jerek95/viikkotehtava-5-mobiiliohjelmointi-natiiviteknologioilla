package com.example.viikko5.data.repository

import coil.network.HttpException
import com.example.viikko5.BuildConfig
import com.example.viikko5.data.model.WeatherResponse
import com.example.viikko5.data.remote.RetrofitClient
import com.example.viikko5.util.Result
import java.io.IOException

class WeatherRepository {


    private val apiService = RetrofitClient.apiService

    private val apiKey = BuildConfig.api_key


    suspend fun getWeather(city: String): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeather(city, apiKey)
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(Exception("Verkkovirhe: ${e.message}"))
        } catch (e: HttpException) {
            Result.Error(Exception("Palvelinvirhe: ${e.message}"))
        } catch (e: Exception) {
            Result.Error(Exception("Tuntematon virhe: ${e.message}"))
        }
    }
}