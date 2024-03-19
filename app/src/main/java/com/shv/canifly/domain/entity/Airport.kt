package com.shv.canifly.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Airport(
    val id: String,
    val ident: String,
    val type: AirportType?,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val approximateRadius: Int
) : Parcelable

