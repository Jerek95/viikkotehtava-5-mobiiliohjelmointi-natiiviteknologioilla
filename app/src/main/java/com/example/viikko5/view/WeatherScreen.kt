package com.example.viikko5.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
    val city by viewModel.selectedCity.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val apiKey = BuildConfig.api_key

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = it, onValueChange = viewmodel.selectCity(it), label = "City...")
        Button(onClick = {viewmodel.getWeather(city)}) {
            Text("Hae Sää")
        }
    }

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

