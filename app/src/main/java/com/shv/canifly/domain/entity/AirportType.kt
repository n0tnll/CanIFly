package com.shv.canifly.domain.entity

enum class AirportType(val nfzRadius: Double) {
    LARGE_AIRPORT(5000.0),
    MEDIUM_AIRPORT(3000.0),
    SMALL_AIRPORT(2000.0),
    HELIPORT(1000.0),
    SEAPLANE_BASE(2000.0),
    BALLOON_PORT(2000.0);

    companion object {
        fun typeFromString(type: String): AirportType? {
            return entries.find {
                it.name.lowercase() == type.replace("\"", "")
            }
        }

        fun getRadius(type: String): Int {
            return (typeFromString(type)?.nfzRadius?.div(1000))?.toInt() ?: -1
        }
    }
}
