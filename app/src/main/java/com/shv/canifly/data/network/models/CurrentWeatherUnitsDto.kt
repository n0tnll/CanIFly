package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class CurrentWeatherUnitsDto(
    @SerializedName("apparent_temperature")
    val apparentTemperature: String,
    @SerializedName("cloud_cover")
    val cloudCover: String,
    @SerializedName("interval")
    val interval: String,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("precipitation")
    val precipitation: String,
    @SerializedName("rain")
    val rain: String,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: String,
    @SerializedName("showers")
    val showers: String,
    @SerializedName("snowfall")
    val snowfall: String,
    @SerializedName("temperature_2m")
    val temperature2m: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("weather_code")
    val weatherCode: String,
    @SerializedName("wind_direction_10m")
    val windDirection10m: String,
    @SerializedName("wind_gusts_10m")
    val windGusts10m: String,
    @SerializedName("wind_speed_10m")
    val windSpeed10m: String
)