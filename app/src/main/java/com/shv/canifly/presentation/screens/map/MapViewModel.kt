package com.shv.canifly.presentation.screens.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shv.canifly.domain.location.LocationTracker
import com.shv.canifly.domain.usecases.GetNfzDataUseCase
import com.shv.canifly.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getNfzDataUseCase: GetNfzDataUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    suspend fun getCurrentLocation(): Location? {
        return viewModelScope.async {
            locationTracker.getCurrentLocation()?.let {
                it
            }
        }.await()
    }

    fun getNfz() {
        viewModelScope.launch {
            when(val result = getNfzDataUseCase()) {
                is Resource.Error -> {
                    Log.d("MapViewModel", "Error: ${result.message.toString()}")
                    _state.value = _state.value.copy(
                        nfzList = null,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    Log.d("MapViewModel", result.toString())
                    _state.value = _state.value.copy(
                        nfzList = result.data
                    )
                }
            }
        }
    }
}