package com.shv.canifly.domain.entity

import java.time.LocalDateTime

data class DailyWeather(
    val day: LocalDateTime,
    val precipitationSum: Double,
    val sunrise: String,
    val sunset: String
)
