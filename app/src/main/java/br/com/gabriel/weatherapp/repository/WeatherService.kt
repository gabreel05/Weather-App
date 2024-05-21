package br.com.gabriel.weatherapp.repository

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") q: String
    ): Weather
}
