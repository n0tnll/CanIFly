package com.shv.canifly.domain

import com.shv.canifly.domain.entity.HourlyWeather
import com.shv.canifly.domain.entity.WeatherConditions
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentCondition(): Flow<WeatherConditions>

    fun getHourlyForecast(): Flow<HourlyWeather>
}