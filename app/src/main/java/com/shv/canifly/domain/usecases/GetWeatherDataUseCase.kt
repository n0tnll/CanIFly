package com.shv.canifly.domain.usecases

import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.repository.Repository
import com.shv.canifly.domain.util.Resource
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(lat: Double, long: Double): Resource<WeatherInfo> {
        return repository.getWeatherData(lat, long)
    }
}