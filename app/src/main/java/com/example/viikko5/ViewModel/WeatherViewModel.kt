package com.example.viikko5.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viikko5.data.model.Weather
import com.example.viikko5.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel(){
    private val _selectedCity = MutableStateFlow<String?>("")
    val selectedCity: StateFlow<String?> = _selectedCity

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _weatherInfo = MutableStateFlow<List<Weather>>(emptyList())
    val weatherInfo: StateFlow<List<Weather>> = _weatherInfo.asStateFlow()

    fun selectCity(city: String){
        _selectedCity.value = city
    }

    fun getWeather(city: String){

    }

    fun loadWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val userList = RetrofitClient.apiService.getWeather()
                _weatherInfo.value = userList
            } catch (e: Exception) {
                _error.value = "Virhe ladattaessa käyttäjiä: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}