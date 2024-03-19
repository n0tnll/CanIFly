package com.shv.canifly.domain.repository

import com.shv.canifly.domain.entity.Airport
import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.util.Resource

interface Repository {

    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>

    suspend fun getNfzData(): Resource<List<Airport>>
}