package com.example.viikko5.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko5.ViewModel.WeatherViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun WeatherScreen(modifier: Modifier = Modifier, viewmodel : WeatherViewModel = viewModel()){
    val city by viewmodel.selectedCity.collectAsState()
    val isLoading by viewmodel.isLoading.collectAsState()
    val error by viewmodel.error.collectAsState()
    val apiKey = BuildConfig.api_key
    val uiState by viewmodel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = city, onValueChange = viewmodel.selectCity(it), label = "City...")
        Button(onClick = {viewmodel.getWeather()}) {
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
                //WeatherContent(weather = state.data)
                val weather = uiState.data
                Text(
                    text = "${weather.name}, ${weather.sys.country}",
                    //style = MaterialTheme.typography.headlineMedium
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
                    //style = MaterialTheme.typography.displayLarge
                )

                Text(
                    text = weather.weather[0].description.replaceFirstChar { it.uppercase() },
                    //style = MaterialTheme.typography.titleLarge
                )
            }
            is Result.Error -> {
                ErrorScreen(
                    message = state.exception.message ?: "Virhe",
                    onRetry = { viewmodel.getWeather() }
                )
            }
        }
    }
/*
    LaunchedEffect(Unit) {
        viewmodel.loadWeather()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Virhe: $error")
                    Button(onClick = { viewModel.loadUsers() }) {
                        Text("Yritä uudelleen")
                    }
                }
            }
            users.isEmpty() -> {
                Text(
                    "Ei käyttäjiä",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn {
                    items(users) { user ->
                        UserItem(user)
                    }
                }
            }
        }
    }
}
*/
