package com.shv.canifly.domain.usecases

import com.shv.canifly.domain.WeatherRepository
import com.shv.canifly.domain.entity.HourlyWeather
import kotlinx.coroutines.flow.Flow

class GetHourlyForecastUseCase(private val repository: WeatherRepository) {

    operator fun invoke(): Flow<HourlyWeather> {
        return repository.getHourlyForecast()
    }
}