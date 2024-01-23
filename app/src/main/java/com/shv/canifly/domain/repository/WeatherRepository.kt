package com.shv.canifly.domain.repository

import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.util.Resource

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}