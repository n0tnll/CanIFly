package com.shv.canifly.domain.entity

data class WeatherConditions(
    val latitude: Double,
    val longitude: Double,
    val timeZone: String,
    val timeZoneAbbreviation: String,
    val currentWeather: CurrentWeather
)