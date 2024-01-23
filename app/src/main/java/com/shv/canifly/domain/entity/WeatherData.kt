package com.shv.canifly.domain.entity

data class WeatherData(
    val hourWeather: HourlyWeather,
    val dayCondition: DailyWeather
)
