package com.example.viikko5.data.remote

import com.example.viikko5.data.model.WeatherResponse

interface WeatherApiService {
    suspend fun getWeather(city: String): WeatherResponse
}