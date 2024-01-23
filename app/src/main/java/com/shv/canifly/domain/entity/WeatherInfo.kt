package com.shv.canifly.domain.entity

data class WeatherInfo(
    val weatherPerDayData: Map<Int, List<HourlyWeather>>,
    val weatherDailyData: Map<Int, List<DailyWeather>>,
    val currentWeatherData: HourlyWeather?,
    val units: WeatherUnits
)
