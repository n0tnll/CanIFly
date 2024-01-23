package com.shv.canifly.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CurrentWeather(
    val lat: Double,
    val long: Double,
    val currentTime: Long,
    val temperature2m: Double,
    val apparentTemperature: Double,
    val isDay: Int,
    val precipitation: Double,
    val rain: Double,
    val showers: Double,
    val snowfall: Double,
    val weatherCode: WeatherType,
    val cloudCover: Int,
    val windSpeed10M: Double,
    val windDirection10M: Int,
    val windGusts10M: Double
) : Parcelable
