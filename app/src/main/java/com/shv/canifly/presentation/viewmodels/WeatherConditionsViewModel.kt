package com.shv.canifly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shv.canifly.domain.location.LocationTracker
import com.shv.canifly.domain.usecases.GetWeatherDataUseCase
import com.shv.canifly.domain.util.Resource
import com.shv.canifly.presentation.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class WeatherConditionsViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    fun loadWeatherInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            locationTracker.getCurrentLocation()?.let { location ->
                when(val result = getWeatherDataUseCase(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
}