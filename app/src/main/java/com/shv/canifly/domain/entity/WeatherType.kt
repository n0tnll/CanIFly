package com.shv.canifly.domain.entity

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.shv.canifly.R
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconResDay: Int,
    @DrawableRes val iconResNight: Int
) : Parcelable {

    data object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        iconResDay = R.drawable.ic_clear_day,
        iconResNight = R.drawable.ic_clear_night
    )

    data object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconResDay = R.drawable.ic_mainly_clear_day,
        iconResNight = R.drawable.ic_mainly_clear_night
    )

    data object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconResDay = R.drawable.ic_overcast_day,
        iconResNight = R.drawable.ic_overcast_hight
    )

    data object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconResDay = R.drawable.ic_overcast_day,
        iconResNight = R.drawable.ic_overcast_hight
    )

    data object Fog : WeatherType(
        weatherDesc = "Fog",
        iconResDay = R.drawable.ic_fog_day,
        iconResNight = R.drawable.ic_fog_night
    )

    data object RimeFog : WeatherType(
        weatherDesc = "Rime fog",
        iconResDay = R.drawable.ic_freezing_fog_day,
        iconResNight = R.drawable.ic_freezing_fog_night
    )

    data object DrizzleLight : WeatherType(
        weatherDesc = "Drizzle light",
        iconResDay = R.drawable.ic_light_drizzle_day,
        iconResNight = R.drawable.ic_light_drizzle_night
    )

    data object DrizzleModerate : WeatherType(
        weatherDesc = "Drizzle moderate",
        iconResDay = R.drawable.ic_light_drizzle_day,
        iconResNight = R.drawable.ic_light_drizzle_night
    )

    data object DrizzleDenseIntensity : WeatherType(
        weatherDesc = "Drizzle dense intensity",
        iconResDay = R.drawable.ic_light_drizzle_day,
        iconResNight = R.drawable.ic_light_drizzle_night
    )

    data object FreezingDrizzleLight : WeatherType(
        weatherDesc = "Freezing drizzle light",
        iconResDay = R.drawable.ic_freezing_drizzle_day,
        iconResNight = R.drawable.ic_freezing_drizzle_night
    )

    data object FreezingDrizzleDenseIntensity : WeatherType(
        weatherDesc = "Freezing drizzle dense intensity",
        iconResDay = R.drawable.ic_heavy_freezing_drizzle_day,
        iconResNight = R.drawable.ic_heavy_freezing_drizzle_night
    )

    data object RainSlight : WeatherType(
        weatherDesc = "Rain slight",
        iconResDay = R.drawable.ic_light_rain_day,
        iconResNight = R.drawable.ic_light_rain_night
    )

    data object RainModerate : WeatherType(
        weatherDesc = "Rain moderate",
        iconResDay = R.drawable.ic_moderate_rain_day,
        iconResNight = R.drawable.ic_moderate_rain_night
    )

    data object RainHeavyIntensity : WeatherType(
        weatherDesc = "Rain heavy intensity",
        iconResDay = R.drawable.ic_heavy_rain_day,
        iconResNight = R.drawable.ic_heavy_rain_night
    )

    data object FreezingRainLight : WeatherType(
        weatherDesc = "Freezing rain light",
        iconResDay = R.drawable.ic_light_freezing_rain_day,
        iconResNight = R.drawable.ic_light_freezing_rain_night
    )

    data object FreezingRainHeavyIntensity : WeatherType(
        weatherDesc = "Freezing rain heavy intensity",
        iconResDay = R.drawable.ic_moderate_or_heavy_freezing_rain_day,
        iconResNight = R.drawable.ic_moderate_or_heavy_freezing_rain_night
    )

    data object SnowFallSlight : WeatherType(
        weatherDesc = "Snow fall slight",
        iconResDay = R.drawable.ic_light_snow_day,
        iconResNight = R.drawable.ic_light_snow_night
    )

    data object SnowFallModerate : WeatherType(
        weatherDesc = "Snow fall moderate",
        iconResDay = R.drawable.ic_moderate_snow_day,
        iconResNight = R.drawable.ic_moderate_snow_night
    )

    data object SnowFallHeavy : WeatherType(
        weatherDesc = "Snow fall heavy",
        iconResDay = R.drawable.ic_heavy_snow_day,
        iconResNight = R.drawable.ic_heavy_snow_night
    )

    data object SnowGrains : WeatherType(
        weatherDesc = "Snow grains",
        iconResDay = R.drawable.ic_ice_pellets_day,
        iconResNight = R.drawable.ic_ice_pellets_night
    )

    data object RainShowersSlight : WeatherType(
        weatherDesc = "Rain showers slight",
        iconResDay = R.drawable.ic_light_rain_shower_day,
        iconResNight = R.drawable.ic_light_rain_shower_night
    )

    data object RainShowersModerate : WeatherType(
        weatherDesc = "Rain showers moderate",
        iconResDay = R.drawable.ic_moderate_or_heavy_rain_shower_day,
        iconResNight = R.drawable.ic_moderate_or_heavy_rain_shower_night
    )

    data object RainShowersViolent : WeatherType(
        weatherDesc = "Rain showers violent",
        iconResDay = R.drawable.ic_torrential_rain_shower_day,
        iconResNight = R.drawable.ic_torrential_rain_shower_night
    )

    data object SnowShowersSlight : WeatherType(
        weatherDesc = "Snow showers slight",
        iconResDay = R.drawable.ic_light_snow_showers_day,
        iconResNight = R.drawable.ic_light_snow_showers_night
    )

    data object SnowShowersHeavy : WeatherType(
        weatherDesc = "Snow showers heavy",
        iconResDay = R.drawable.ic_moderate_or_heavy_snow_showers_day,
        iconResNight = R.drawable.ic_moderate_or_heavy_snow_showers_night
    )

    data object ThunderstormSlightOrModerate : WeatherType(
        weatherDesc = "Thunderstorm slight or moderate",
        iconResDay = R.drawable.ic_patchy_light_rain_with_thunder_day,
        iconResNight = R.drawable.ic_patchy_light_rain_with_thunder_night
    )

    data object ThunderstormWithSlight : WeatherType(
        weatherDesc = "Thunderstorm with slight",
        iconResDay = R.drawable.ic_patchy_light_rain_with_thunder_day,
        iconResNight = R.drawable.ic_patchy_light_rain_with_thunder_night
    )

    data object ThunderstormWithHeavyHail : WeatherType(
        weatherDesc = "Thunderstorm with heavy hail",
        iconResDay = R.drawable.ic_moderate_or_heavy_rain_with_thunder_day,
        iconResNight = R.drawable.ic_moderate_or_heavy_rain_with_thunder_night
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

