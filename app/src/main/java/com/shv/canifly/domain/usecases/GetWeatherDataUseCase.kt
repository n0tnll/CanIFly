package com.shv.canifly.domain.usecases

import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.repository.WeatherRepository
import com.shv.canifly.domain.util.Resource

class GetWeatherDataUseCase(private val repository: WeatherRepository) {

    suspend operator fun invoke(lat: Double, long: Double): Resource<WeatherInfo> {
        return repository.getWeatherData(lat, long)
    }
}