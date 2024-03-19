package com.shv.canifly.data.network.api

import com.shv.canifly.data.network.models.WeatherDataDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/ourairports-data/airports.csv")
    suspend fun getNFZDataCSV(): Response<ResponseBody>

    @GET("/v1/forecast")
    suspend fun getWeatherConditionData(
        @Query(QUERY_PARAM_LATITUDE) lat: Double,
        @Query(QUERY_PARAM_LONGITUDE) long: Double,
        @Query(QUERY_PARAM_HOURLY) hourly: String = PARAMS_HOURLY,
        @Query(QUERY_PARAM_DAILY) daily: String = PARAMS_DAILY,
        @Query(QUERY_PARAM_WIND_SPEED_UNIT) windSpeedUnit: String = PARAMS_WIND_SPEED_UNIT,
        @Query(QUERY_PARAM_TIMEZONE) timezone: String = PARAMS_TIMEZONE,
        @Query(QUERY_PARAM_MODELS) models: String = PARAMS_MODELS,
    ): WeatherDataDto

    companion object {
        private const val QUERY_PARAM_LATITUDE = "latitude"
        private const val QUERY_PARAM_LONGITUDE = "longitude"
        private const val QUERY_PARAM_HOURLY = "hourly"
        private const val QUERY_PARAM_DAILY = "daily"
        private const val QUERY_PARAM_WIND_SPEED_UNIT = "wind_speed_unit"
        private const val QUERY_PARAM_TIMEZONE = "timezone"
        private const val QUERY_PARAM_MODELS = "models"

        private const val PARAMS_HOURLY = "temperature_2m,apparent_temperature," +
                "precipitation_probability,precipitation,rain,showers,snowfall,weather_code," +
                "cloud_cover,cloud_cover_low,visibility,wind_speed_10m,wind_speed_120m," +
                "wind_direction_10m,wind_direction_120m,wind_gusts_10m,temperature_120m,is_day," +
                "temperature_975hPa,temperature_950hPa,temperature_925hPa,temperature_900hPa," +
                "windspeed_975hPa,windspeed_950hPa,windspeed_925hPa,windspeed_900hPa," +
                "winddirection_975hPa,winddirection_950hPa,winddirection_925hPa,winddirection_900hPa"
        private const val PARAMS_DAILY = "sunrise,sunset,precipitation_probability_max," +
                "temperature_2m_max,temperature_2m_min,weather_code"
        private const val PARAMS_WIND_SPEED_UNIT = "ms"
        private const val PARAMS_TIMEZONE = "auto"
        private const val PARAMS_MODELS = "best_match"

    }
}