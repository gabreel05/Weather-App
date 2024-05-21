package br.com.gabriel.weatherapp.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabriel.weatherapp.repository.Weather
import br.com.gabriel.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherRepository = WeatherRepository()

    val weather: MutableState<Weather> = mutableStateOf(Weather())

    fun fetchWeather(q: String = "Curitiba") {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeather(q)

                println(response.toString())

                weather.value = response
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}
