package com.shv.canifly.data.repository

import com.shv.canifly.data.mapper.toWeatherInfo
import com.shv.canifly.data.network.api.WeatherApiService
import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.repository.WeatherRepository
import com.shv.canifly.domain.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = weatherApiService.getWeatherConditionData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}