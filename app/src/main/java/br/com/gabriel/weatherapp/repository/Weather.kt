package br.com.gabriel.weatherapp.repository

data class Weather(
    val current: Current? = null,
    val location: Location? = null
)