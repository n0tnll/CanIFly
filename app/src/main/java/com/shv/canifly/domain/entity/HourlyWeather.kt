package com.shv.canifly.domain.entity

data class HourlyWeather(
    val times: List<Long>,
    val temperatures2m: List<Double>,
    val apparentTemperatures: List<Double>,
    val precipitationProbability: List<Int>,
    val precipitation: List<Double>,
    val rain: List<Double>,
    val showers: List<Double>,
    val snowfall: List<Double>,
    val snowDepth: List<Double>,
    val weatherCode: WeatherCode,
    val cloudCover: List<Int>,
    val cloudCoverLow: List<Int>,
    val visibility: List<Int>,
    val windSpeed10m: List<Double>,
    val windDirection10m: List<Double>,
    val windGusts10m: List<Double>,
    val isDay: Boolean,
    )
