package com.shv.canifly.domain.entity

import java.time.LocalDateTime

data class HourlyWeather(
    val time: LocalDateTime,
    val temperature2m: Double,
    val apparentTemperature: Double,
    val precipitationProbability: Int,
    val precipitation: Double,
    val windSpeed10m: Double,
    val windDirection10m: Int,
    val windGusts10m: Double,
    val visibility: Int,
    val cloudCover: Int,
    val cloudCoverLow: Int,
    val rain: Double,
    val showers: Double,
    val snowfall: Double,
    val weatherType: WeatherType,
    val isDay: Int
)