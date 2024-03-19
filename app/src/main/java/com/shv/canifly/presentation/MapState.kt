package com.shv.canifly.presentation

import com.shv.canifly.domain.entity.Airport

data class MapState(
    val nfzList: List<Airport>? = null,
    val error: String? = null
)
