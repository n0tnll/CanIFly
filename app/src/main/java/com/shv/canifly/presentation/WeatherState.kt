package com.shv.canifly.presentation

import com.shv.canifly.domain.entity.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
