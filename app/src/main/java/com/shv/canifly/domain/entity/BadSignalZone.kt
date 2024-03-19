package com.shv.canifly.domain.entity

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class BadSignalZone(
    val description: String,
    val radius: Int,
    val latLng: LatLng
) : Parcelable
