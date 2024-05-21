package br.com.gabriel.weatherapp.repository

class WeatherRepository {
    private val weatherRepository = WeatherAPI.instance

    suspend fun getWeather(q: String = "Curitiba"): Weather {
        return weatherRepository.getWeather("579a1281fa2f4496bdd184940242005", q)
    }
}
