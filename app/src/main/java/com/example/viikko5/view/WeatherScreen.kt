package com.example.viikko5.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko5.ViewModel.WeatherViewModel
import com.example.viikko5.util.Result
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun WeatherScreen(modifier: Modifier = Modifier, viewmodel : WeatherViewModel = viewModel()) {
    val city by viewmodel.selectedCity.collectAsState()
    val uiState by viewmodel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        city?.let { it1 ->
            TextField(
                value = it1,
                onValueChange = { viewmodel.selectCity(it) },
                label = { Text("City...") })
        }
        Button(onClick = { viewmodel.getWeather() }) {
            Text("Hae Sää")
        }
        when (val state = uiState) {
            is Result.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is Result.Success -> {
                val weather = state.data
                Text(
                    text = "${weather.name}, ${weather.sys.country}",
                )

                Spacer(modifier = Modifier.height(16.dp))

                val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@4x.png"
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Sääkuvake",
                    modifier = Modifier.size(120.dp)
                )

                Text(
                    text = "${weather.main.temp.toInt()}°C",
                )

                Text(
                    text = weather.weather[0].description.replaceFirstChar { it.uppercase() },
                )
            }

            is Result.Error -> {
                Text(text="Kaupunkia ei löytynyt")
            }
        }
    }
}
