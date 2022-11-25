package com.example.weather_application

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    companion object {
        const val API: String = "8aeb7606fadca95e4a86df09321997cd"
    }

    //https://api.openweathermap.org/data/2.5/weather?q=warsaw&units=metric&appid=8aeb7606fadca95e4a86df09321997cd

    @GET("?units=metric&appid=$API")
    fun getWeatherByCity(@Query("q") city: String): Call<WeatherResult>
}