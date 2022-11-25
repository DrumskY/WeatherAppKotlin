package com.example.weather_application


    data class WeatherResult(
        var name: String,
        var main: MainJson,
        var dt: Long,
        var sys: SysJson,
        var wind: WindJson,
        var weather: Array<WeatherJson>
    )

    data class MainJson(
        var temp: Double,
        var temp_min: Double,
        var temp_max: Double,
        var pressure: Double,
        var humidity: Int
    )

    data class SysJson(
        var sunrise: Long,
        var sunset: Long
    )

    data class WindJson(
        var speed: Double
    )

    data class WeatherJson(
        var description: String
    )
