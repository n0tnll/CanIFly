package com.shv.canifly.domain.entity

import androidx.annotation.DrawableRes
import com.shv.canifly.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {

    data object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.ic_sunny_day
    )

    data object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.ic_mainly_clear_day
    )

    data object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.ic_partly_cloudy_day
    )

    data object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconRes = R.drawable.ic_overcast_day
    )

    data object Fog : WeatherType(
        weatherDesc = "Fog",
        iconRes = R.drawable.ic_fog_day
    )

    data object RimeFog : WeatherType(
        weatherDesc = "Rime fog",
        iconRes = R.drawable.ic_freezing_fog_day
    )

    data object DrizzleLight : WeatherType(
        weatherDesc = "Drizzle light",
        iconRes = R.drawable.ic_light_drizzle_day
    )

    data object DrizzleModerate : WeatherType(
        weatherDesc = "Drizzle moderate",
        iconRes = R.drawable.ic_light_drizzle_day
    )

    data object DrizzleDenseIntensity : WeatherType(
        weatherDesc = "Drizzle dense intensity",
        iconRes = R.drawable.ic_light_drizzle_day
    )

    data object FreezingDrizzleLight : WeatherType(
        weatherDesc = "Freezing drizzle light",
        iconRes = R.drawable.ic_freezing_drizzle_day
    )

    data object FreezingDrizzleDenseIntensity : WeatherType(
        weatherDesc = "Freezing drizzle dense intensity",
        iconRes = R.drawable.ic_heavy_freezing_drizzle_day
    )

    data object RainSlight : WeatherType(
        weatherDesc = "Rain slight",
        iconRes = R.drawable.ic_light_rain_day
    )

    data object RainModerate : WeatherType(
        weatherDesc = "Rain moderate",
        iconRes = R.drawable.ic_moderate_rain_day
    )

    data object RainHeavyIntensity : WeatherType(
        weatherDesc = "Rain heavy intensity",
        iconRes = R.drawable.ic_heavy_rain_day
    )

    data object FreezingRainLight : WeatherType(
        weatherDesc = "Freezing rain light",
        iconRes = R.drawable.ic_light_freezing_rain_day
    )

    data object FreezingRainHeavyIntensity : WeatherType(
        weatherDesc = "Freezing rain heavy intensity",
        iconRes = R.drawable.ic_moderate_or_heavy_freezing_rain_day
    )

    data object SnowFallSlight : WeatherType(
        weatherDesc = "Snow fall slight",
        iconRes = R.drawable.ic_light_snow_day
    )

    data object SnowFallModerate : WeatherType(
        weatherDesc = "Snow fall moderate",
        iconRes = R.drawable.ic_moderate_snow_day
    )

    data object SnowFallHeavy : WeatherType(
        weatherDesc = "Snow fall heavy",
        iconRes = R.drawable.ic_heavy_snow_day
    )

    data object SnowGrains : WeatherType(
        weatherDesc = "Snow grains",
        iconRes = R.drawable.ic_ice_pellets_day
    )

    data object RainShowersSlight : WeatherType(
        weatherDesc = "Rain showers slight",
        iconRes = R.drawable.ic_light_rain_shower_day
    )

    data object RainShowersModerate : WeatherType(
        weatherDesc = "Rain showers moderate",
        iconRes = R.drawable.ic_moderate_or_heavy_rain_shower_day
    )

    data object RainShowersViolent : WeatherType(
        weatherDesc = "Rain showers violent",
        iconRes = R.drawable.ic_torrential_rain_shower_day
    )

    data object SnowShowersSlight : WeatherType(
        weatherDesc = "Snow showers slight",
        iconRes = R.drawable.ic_light_snow_showers_day
    )

    data object SnowShowersHeavy : WeatherType(
        weatherDesc = "Snow showers heavy",
        iconRes = R.drawable.ic_moderate_or_heavy_snow_showers_day
    )

    data object ThunderstormSlightOrModerate : WeatherType(
        weatherDesc = "Thunderstorm slight or moderate",
        iconRes = R.drawable.ic_patchy_light_rain_with_thunder_day
    )

    data object ThunderstormWithSlight : WeatherType(
        weatherDesc = "Thunderstorm with slight",
        iconRes = R.drawable.ic_patchy_light_rain_with_thunder_day
    )

    data object ThunderstormWithHeavyHail : WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        iconRes = R.drawable.ic_moderate_or_heavy_rain_with_thunder_day
    )


    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Fog
                48 -> RimeFog
                51 -> DrizzleLight
                53 -> DrizzleModerate
                55 -> DrizzleDenseIntensity
                56 -> FreezingDrizzleLight
                57 -> FreezingDrizzleDenseIntensity
                61 -> RainSlight
                63 -> RainModerate
                65 -> RainHeavyIntensity
                66 -> FreezingRainLight
                67 -> FreezingRainHeavyIntensity
                71 -> SnowFallSlight
                73 -> SnowFallModerate
                75 -> SnowFallHeavy
                77 -> SnowGrains
                80 -> RainShowersSlight
                81 -> RainShowersModerate
                82 -> RainShowersViolent
                85 -> SnowShowersSlight
                86 -> SnowShowersHeavy
                95 -> ThunderstormSlightOrModerate
                96 -> ThunderstormWithSlight
                99 -> ThunderstormWithHeavyHail
                else -> {
                    throw RuntimeException("Unknown weather code: $code")
                }
            }
        }
    }
}

