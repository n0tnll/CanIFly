package com.shv.canifly.data.extensions

import java.time.LocalDate
import java.time.LocalDateTime
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

fun LocalDate.shortDayOfWeek(): String {
    return format(
        DateTimeFormatter.ofPattern(
            "EEE",
            Locale.getDefault()
        )
    )
}
