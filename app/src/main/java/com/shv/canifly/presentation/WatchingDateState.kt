package com.shv.canifly.presentation

import com.shv.canifly.domain.entity.WatchingDate

data class WatchingDateState(
    val isListEmpty: Boolean? = null,
    val watchingDates: List<WatchingDate>? = null
)