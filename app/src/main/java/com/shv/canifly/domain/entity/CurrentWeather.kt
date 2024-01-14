package com.shv.canifly.domain.entity

data class CurrentWeather(
    val currentTime: Long,
    val temperature2m: Double,
    val apparentTemperature: Double,
    val isDay: Boolean,
    val precipitation: Double,
    val rain: Double,
    val showers: Double,
    val snowfall: Double,
    val weatherCode: WeatherCode,
    val cloudCover: Int,
    val windSpeed10M: Double,
    val windDirection10M: Int,
    val windGusts10M: Double
)
