package com.shv.canifly.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.shv.canifly.domain.usecases.AddWatchingDateUseCase
import com.shv.canifly.presentation.WatchingDateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WatchingDatesViewModel @Inject constructor(
    private val addWatchingDateUseCase: AddWatchingDateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WatchingDateState())
    val state = _state.asStateFlow()


}