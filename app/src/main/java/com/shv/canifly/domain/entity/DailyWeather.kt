package com.shv.canifly.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyWeather(
    val day: LocalDate,
    val temperatureMax: Double,
    val temperatureMin: Double,
    val precipitationProbabilityMax: Int,
    val weatherType: WeatherType,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime
)
