package com.shv.canifly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.shv.canifly.domain.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationTracker: LocationTracker
) : ViewModel() {

}