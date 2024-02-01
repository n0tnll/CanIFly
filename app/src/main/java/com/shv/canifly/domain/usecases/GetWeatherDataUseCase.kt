package com.shv.canifly.domain.usecases

import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.repository.WeatherRepository
import com.shv.canifly.domain.util.Resource
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(lat: Double, long: Double): Resource<WeatherInfo> {
        return repository.getWeatherData(lat, long)
    }
}