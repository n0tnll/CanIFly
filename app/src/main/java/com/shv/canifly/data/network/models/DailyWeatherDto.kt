package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class DailyWeatherDto(
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int>,
    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double>,
    @SerializedName("sunrise")
    val sunrise: List<String>,
    @SerializedName("sunset")
    val sunset: List<String>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>
)