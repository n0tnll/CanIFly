package com.shv.canifly.domain.entity

import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

data class WatchingDate(
    val dateTime: LocalDateTime,
    val conditions: HourlyWeather,
    val location: LatLng ? = null,
    var flyStatus: Boolean,
    var isCompleted: Boolean
)
