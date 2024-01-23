package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class WeatherDataDto(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int,
    @SerializedName("current")
    val currentWeatherDto: CurrentWeatherDto,
    @SerializedName("current_units")
    val currentWeatherUnitsDto: CurrentWeatherUnitsDto,
    @SerializedName("daily")
    val dailyWeatherDto: DailyWeatherDto,
    @SerializedName("daily_units")
    val dailyWeatherUnitsDto: DailyWeatherUnitsDto,
    @SerializedName("elevation")
    val elevation: Double,
    @SerializedName("hourly")
    val hourlyWeatherDto: HourlyWeatherDto,
    @SerializedName("hourly_units")
    val hourlyWeatherUnitsDto: HourlyWeatherUnitsDto
)