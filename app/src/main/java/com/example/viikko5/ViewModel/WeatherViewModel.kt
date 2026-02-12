package com.example.viikko5.ViewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class WeatherViewModel : ViewModel(){
    private val _selectedCity = MutableStateFlow<String?>("")
    val selectedCity: StateFlow<String?> = _selectedCity

    fun selectCity(city: String){
        _selectedCity.value = city
    }

    fun getWeather(city: String){

    }
}