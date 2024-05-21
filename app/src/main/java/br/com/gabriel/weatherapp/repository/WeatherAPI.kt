package br.com.gabriel.weatherapp.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherAPI {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}
