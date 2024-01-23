package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class DailyWeatherUnitsDto(
    @SerializedName("time")
    val time: String,
    @SerializedName("precipitation_sum")
    val precipitationSum: String,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String
)