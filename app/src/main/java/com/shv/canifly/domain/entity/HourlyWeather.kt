package com.shv.canifly.domain.entity

import java.time.LocalDateTime

data class HourlyWeather(
    val time: LocalDateTime,
    val temperature2m: Double,
    val temperature120m: Double,
    val temperature320m: Double,
    val temperature500m: Double,
    val temperature800m: Double,
    val temperature1000m: Double,
    val apparentTemperature: Double,
    val precipitationProbability: Int,
    val precipitation: Double,
    val windSpeed10m: Double,
    val windSpeed120m: Double,
    val windSpeed320m: Double,
    val windSpeed500m: Double,
    val windSpeed800m: Double,
    val windSpeed1000m: Double,
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