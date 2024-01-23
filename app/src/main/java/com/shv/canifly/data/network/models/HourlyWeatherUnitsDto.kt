package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class HourlyWeatherUnitsDto(
    @SerializedName("cloud_cover")
    val cloudCover: String,
    @SerializedName("precipitation")
    val precipitation: String,
    @SerializedName("precipitation_probability")
    val precipitationProbability: String,
    @SerializedName("rain")
    val rain: String,
    @SerializedName("showers")
    val showers: String,
    @SerializedName("snowfall")
    val snowfall: String,
    @SerializedName("temperature_2m")
    val temperature: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("wind_direction_10m")
    val windDirection: String,
    @SerializedName("wind_gusts_10m")
    val windGusts: String,
    @SerializedName("wind_speed_10m")
    val windSpeed: String
)