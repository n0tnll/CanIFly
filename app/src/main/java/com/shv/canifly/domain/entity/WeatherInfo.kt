package com.shv.canifly.domain.entity

data class WeatherInfo(
    val weatherPerDayData: Map<Int, List<HourlyWeather>>,
    val weatherDailyData: List<DailyWeather>?,
    val currentWeatherData: HourlyWeather?,
    val currentDailyWeatherData: DailyWeather?,
    val units: WeatherUnits
)
