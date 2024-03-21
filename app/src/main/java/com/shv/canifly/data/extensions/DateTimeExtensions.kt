package com.shv.canifly.data.extensions

import android.content.Context
import com.shv.canifly.R
import com.shv.canifly.domain.entity.DailyWeather
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.toTimeString(): String {
    return format(
        DateTimeFormatter.ofPattern(
            "HH:mm",
            Locale.getDefault()
        )
    )
}

fun LocalDateTime.toDateString(): String {
    return format(
        DateTimeFormatter.ofPattern(
            "yyyy.MM.dd",
            Locale.getDefault()
        )
    )
}

fun LocalDate.shortDayOfWeek(): String {
    return format(
        DateTimeFormatter.ofPattern(
            "EEE",
            Locale.getDefault()
        )
    )
}

fun LocalDate.detailedDayString(): String {
    return  format(
        DateTimeFormatter.ofPattern(
            "EEEE yyyy.MM.dd",
            Locale.getDefault()
        )
    )
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun DayOfWeek.toNormalDayName(): String {
    return toString().lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

fun DailyWeather.getDayName(currentDay: String, context: Context): CharSequence =
    if (LocalDate.now().dayOfMonth == day.dayOfMonth) context.getString(R.string.today) else currentDay
