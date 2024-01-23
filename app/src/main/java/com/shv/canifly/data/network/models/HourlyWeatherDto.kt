package com.shv.canifly.data.network.models


import com.google.gson.annotations.SerializedName

data class HourlyWeatherDto(
    @SerializedName("apparent_temperature")
    val apparentTemperature: List<Double>,
    @SerializedName("cloud_cover")
    val cloudCover: List<Int>,
    @SerializedName("cloud_cover_low")
    val cloudCoverLow: List<Int>,
    @SerializedName("is_day")
    val isDay: List<Int>,
    @SerializedName("precipitation")
    val precipitation: List<Double>,
    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>,
    @SerializedName("rain")
    val rain: List<Double>,
    @SerializedName("showers")
    val showers: List<Double>,
    @SerializedName("snowfall")
    val snowfall: List<Double>,
    @SerializedName("temperature_1000hPa")
    val temperature1000hPa: List<Double>,
    @SerializedName("temperature_120m")
    val temperature120m: List<Double>,
    @SerializedName("temperature_180m")
    val temperature180m: List<Double>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("temperature_900hPa")
    val temperature1000m: List<Double>,
    @SerializedName("temperature_925hPa")
    val temperature800m: List<Double>,
    @SerializedName("temperature_950hPa")
    val temperature500m: List<Double>,
    @SerializedName("temperature_975hPa")
    val temperature320m: List<Double>,
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("visibility")
    val visibility: List<Double>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>,
    @SerializedName("wind_direction_10m")
    val windDirection10m: List<Int>,
    @SerializedName("wind_direction_120m")
    val windDirection120m: List<Int>,
    @SerializedName("wind_direction_180m")
    val windDirection180m: List<Int>,
    @SerializedName("wind_gusts_10m")
    val windGusts10m: List<Double>,
    @SerializedName("wind_speed_10m")
    val windSpeed10m: List<Double>,
    @SerializedName("wind_speed_120m")
    val windSpeed120m: List<Double>,
    @SerializedName("wind_speed_180m")
    val windSpeed180m: List<Double>,
    @SerializedName("winddirection_900hPa")
    val windDirection1000m: List<Int>,
    @SerializedName("winddirection_925hPa")
    val windDirection800m: List<Int>,
    @SerializedName("winddirection_950hPa")
    val windDirection500m: List<Int>,
    @SerializedName("winddirection_975hPa")
    val windDirection320m: List<Int>,
    @SerializedName("windspeed_900hPa")
    val windSpeed1000m: List<Double>,
    @SerializedName("windspeed_925hPa")
    val windSpeed800m: List<Double>,
    @SerializedName("windspeed_950hPa")
    val windSpeed500m: List<Double>,
    @SerializedName("windspeed_975hPa")
    val windSpeed320: List<Double>
)