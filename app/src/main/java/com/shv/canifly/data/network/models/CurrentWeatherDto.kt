package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("apparent_temperature")
    val apparentTemperature: Double,
    @SerializedName("cloud_cover")
    val cloudCover: Int,
    @SerializedName("interval")
    val interval: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precipitation")
    val precipitation: Double,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: Int,
    @SerializedName("showers")
    val showers: Double,
    @SerializedName("snowfall")
    val snowfall: Double,
    @SerializedName("temperature_2m")
    val temperature2m: Double,
    @SerializedName("time")
    val time: String,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("wind_direction_10m")
    val windDirection10m: Int,
    @SerializedName("wind_gusts_10m")
    val windGusts10m: Double,
    @SerializedName("wind_speed_10m")
    val windSpeed10m: Double
)