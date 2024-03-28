package com.shv.canifly.presentation.screens.favorites

import com.shv.canifly.domain.entity.WatchingDate

data class WatchingDateState(
    val isListEmpty: Boolean? = null,
    val watchingDates: List<WatchingDate>? = null
)