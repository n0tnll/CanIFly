package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class DailyWeatherDto(
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("precipitation_sum")
    val precipitationSum: List<Double>,
    @SerializedName("sunrise")
    val sunrise: List<String>,
    @SerializedName("sunset")
    val sunset: List<String>
)