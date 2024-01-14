package com.shv.canifly.domain.usecases

import com.shv.canifly.domain.WeatherRepository
import com.shv.canifly.domain.entity.WeatherConditions
import kotlinx.coroutines.flow.Flow

class GetCurrentConditionUseCase(private val repository: WeatherRepository) {

    operator fun invoke(): Flow<WeatherConditions> {
        return repository.getCurrentCondition()
    }
}