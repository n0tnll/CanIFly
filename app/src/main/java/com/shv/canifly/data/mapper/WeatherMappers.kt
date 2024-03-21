package com.shv.canifly.data.mapper

import com.shv.canifly.data.network.models.WeatherDataDto
import com.shv.canifly.domain.entity.DailyWeather
import com.shv.canifly.domain.entity.HourlyWeather
import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.entity.WeatherType
import com.shv.canifly.domain.entity.WeatherUnits
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private data class IndexedHourlyWeather(
    val index: Int,
    val hourlyWeather: HourlyWeather
)

fun WeatherDataDto.mapDailyWeatherMap(): List<DailyWeather> {
    val map = dailyWeatherDto.time.mapIndexed { index, day ->
        val precipitationProbabilityMax = dailyWeatherDto.precipitationProbabilityMax[index]
        val sunrise = dailyWeatherDto.sunrise[index]
        val sunset = dailyWeatherDto.sunset[index]
        val weatherCode = dailyWeatherDto.weatherCode[index]
        val temperatureMax = dailyWeatherDto.temperatureMax[index]
        val temperatureMin = dailyWeatherDto.temperatureMin[index]
        val hourlyWeatherList = getHourlyWeatherPerDayList(index)

        DailyWeather(
            day = LocalDate.parse(
                day,
                DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
            ),
            precipProbMax = precipitationProbabilityMax,
            temperatureMax = temperatureMax,
            temperatureMin = temperatureMin,
            weatherType = WeatherType.fromWMO(weatherCode),
            sunrise = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_DATE_TIME),
            sunset = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_DATE_TIME),
            hourlyWeather = hourlyWeatherList!!
        )
    }
    return map
}

fun WeatherDataDto.mapHourlyWeatherMap(): Map<Int, List<HourlyWeather>> {
    return hourlyWeatherDto.time.mapIndexed { index, time ->
        val temperature2m = hourlyWeatherDto.temperature2m[index]
        val temperature120m = hourlyWeatherDto.temperature120m[index]
        //val temperature180m = hourlyWeatherDto.temperature180m[index]
        val temperature320m = hourlyWeatherDto.temperature320m[index]
        val temperature500m = hourlyWeatherDto.temperature500m[index]
        val temperature800m = hourlyWeatherDto.temperature800m[index]
        val temperature1000m = hourlyWeatherDto.temperature1000m[index]
        val apparentTemperature = hourlyWeatherDto.apparentTemperature[index]
        val windSpeed10m = hourlyWeatherDto.windSpeed10m[index]
        val windSpeed120m = hourlyWeatherDto.windSpeed120m[index]
        //val windSpeed180m = hourlyWeatherDto.windSpeed180m[index]
        val windSpeed320m = hourlyWeatherDto.windSpeed320[index]
        val windSpeed500m = hourlyWeatherDto.windSpeed500m[index]
        val windSpeed800m = hourlyWeatherDto.windSpeed800m[index]
        val windSpeed1000m = hourlyWeatherDto.windSpeed1000m[index]
        val weatherCode = hourlyWeatherDto.weatherCode[index]
        val windDirection10m = hourlyWeatherDto.windDirection10m[index]
        val windDirection120m = hourlyWeatherDto.windDirection120m[index]
        // val windDirection180m = hourlyWeatherDto.windDirection180m[index]
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
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                temperature2m = temperature2m,
                temperature120m = temperature120m,
                temperature320m = temperature320m,
                temperature500m = temperature500m,
                temperature800m = temperature800m,
                temperature1000m = temperature1000m,
                feelsLikeTemp = apparentTemperature,
                precipitationProbability = precipitationProbability,
                precipitation = precipitation,
                windSpeed10m = windSpeed10m,
                windSpeed120m = windSpeed120m,
                windSpeed320m = windSpeed320m,
                windSpeed500m = windSpeed500m,
                windSpeed800m = windSpeed800m,
                windSpeed1000m = windSpeed1000m,
                windDirection10m = windDirection10m,
                windGusts10m = windGusts10m,
                visibility = (visibility / 1000).toInt(),
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

private fun WeatherDataDto.getHourlyWeatherPerDayList(index: Int): List<HourlyWeather>? {
    return mapHourlyWeatherMap()[index]?.toList()
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
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    val currentDailyWeather = dailyWeatherData[0]

    return WeatherInfo(
        weatherPerDayData = hourlyWeatherData,
        weatherDailyData = dailyWeatherData,
        currentWeatherData = currentWeatherData,
        currentDailyWeatherData = currentDailyWeather,
        units = units
    )
}































