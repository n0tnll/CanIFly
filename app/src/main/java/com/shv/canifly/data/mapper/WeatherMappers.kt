package com.shv.canifly.data.mapper

import com.shv.canifly.data.network.models.WeatherDataDto
import com.shv.canifly.domain.entity.DailyWeather
import com.shv.canifly.domain.entity.HourlyWeather
import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.entity.WeatherType
import com.shv.canifly.domain.entity.WeatherUnits
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedHourlyWeather(
    val index: Int,
    val hourlyWeather: HourlyWeather
)

fun WeatherDataDto.mapDailyWeatherMap(): Map<Int, List<DailyWeather>> {
    return dailyWeatherDto.time.mapIndexed { index, day ->
        val precipitationSum = dailyWeatherDto.precipitationSum[index]
        val sunrise = dailyWeatherDto.sunrise[index]
        val sunset = dailyWeatherDto.sunset[index]

        DailyWeather(
            day = LocalDateTime.parse(day, DateTimeFormatter.ISO_DATE_TIME),
            precipitationSum = precipitationSum,
            sunrise = sunrise,
            sunset = sunset
        )
    }.groupBy {
        it.day.dayOfMonth
    }
}

fun WeatherDataDto.mapHourlyWeatherMap(): Map<Int, List<HourlyWeather>> {
    return hourlyWeatherDto.time.mapIndexed { index, time ->
        val temperature2m = hourlyWeatherDto.temperature2m[index]
        val temperature120m = hourlyWeatherDto.temperature120m[index]
        val temperature180m = hourlyWeatherDto.temperature180m[index]
        val temperature320m = hourlyWeatherDto.temperature320m[index]
        val temperature500m = hourlyWeatherDto.temperature500m[index]
        val temperature800m = hourlyWeatherDto.temperature800m[index]
        val temperature1000m = hourlyWeatherDto.temperature1000m[index]
        val apparentTemperature = hourlyWeatherDto.apparentTemperature[index]
        val windSpeed10m = hourlyWeatherDto.windSpeed10m[index]
        val windSpeed120m = hourlyWeatherDto.windSpeed120m[index]
        val windSpeed180m = hourlyWeatherDto.windSpeed180m[index]
        val windSpeed320 = hourlyWeatherDto.windSpeed320[index]
        val windSpeed500m = hourlyWeatherDto.windSpeed500m[index]
        val windSpeed800m = hourlyWeatherDto.windSpeed800m[index]
        val windSpeed1000m = hourlyWeatherDto.windSpeed1000m[index]
        val weatherCode = hourlyWeatherDto.weatherCode[index]
        val windDirection10m = hourlyWeatherDto.windDirection10m[index]
        val windDirection120m = hourlyWeatherDto.windDirection120m[index]
        val windDirection180m = hourlyWeatherDto.windDirection180m[index]
        val windDirection320m = hourlyWeatherDto.windDirection320m[index]
        val windDirection500m = hourlyWeatherDto.windDirection500m[index]
        val windDirection800m = hourlyWeatherDto.windDirection800m[index]
        val windDirection1000m = hourlyWeatherDto.windDirection1000m[index]
        val windGusts10m = hourlyWeatherDto.windGusts10m[index]
        val visibility = hourlyWeatherDto.visibility[index]
        val cloudCover = hourlyWeatherDto.cloudCover[index]
        val cloudCoverLow = hourlyWeatherDto.cloudCoverLow[index]
        val isDay = hourlyWeatherDto.isDay[index]
        val precipitation = hourlyWeatherDto.precipitation[index]
        val precipitationProbability = hourlyWeatherDto.precipitationProbability[index]
        val rain = hourlyWeatherDto.rain[index]
        val showers = hourlyWeatherDto.showers[index]
        val snowfall = hourlyWeatherDto.snowfall[index]

        IndexedHourlyWeather(
            index = index,
            hourlyWeather = HourlyWeather(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperature2m = temperature2m,
                apparentTemperature = apparentTemperature,
                precipitationProbability = precipitationProbability,
                precipitation = precipitation,
                windSpeed10m = windSpeed10m,
                windDirection10m = windDirection10m,
                windGusts10m = windGusts10m,
                visibility = visibility.toInt(),
                cloudCover = cloudCover,
                cloudCoverLow = cloudCoverLow,
                rain = rain,
                showers = showers,
                snowfall = snowfall,
                weatherType = WeatherType.fromWMO(weatherCode),
                isDay = isDay
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { indexedHourlyWeather ->
            indexedHourlyWeather.hourlyWeather
        }
    }
}

fun WeatherDataDto.mapToWeatherUnit(): WeatherUnits {
    return WeatherUnits(
        temperature = hourlyWeatherUnitsDto.temperature,
        cloudCover = hourlyWeatherUnitsDto.cloudCover,
        precipitation = hourlyWeatherUnitsDto.precipitation,
        precipitationProbability = hourlyWeatherUnitsDto.precipitationProbability,
        rain = hourlyWeatherUnitsDto.rain,
        showers = hourlyWeatherUnitsDto.showers,
        snowfall = hourlyWeatherUnitsDto.snowfall,
        visibility = hourlyWeatherUnitsDto.visibility,
        windDirection = hourlyWeatherUnitsDto.windDirection,
        windGusts = hourlyWeatherUnitsDto.windGusts,
        windSpeed = hourlyWeatherUnitsDto.windSpeed
    )
}

fun WeatherDataDto.toWeatherInfo(): WeatherInfo {
    val hourlyWeatherData = mapHourlyWeatherMap()
    val dailyWeatherData = mapDailyWeatherMap()
    val units = mapToWeatherUnit()
    val now = LocalDateTime.now()
    val currentWeatherData = hourlyWeatherData[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour.inc()
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherPerDayData = hourlyWeatherData,
        weatherDailyData = dailyWeatherData,
        currentWeatherData = currentWeatherData,
        units = units
    )
}































