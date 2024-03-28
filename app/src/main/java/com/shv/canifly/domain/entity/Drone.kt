package com.shv.canifly.domain.entity

data class Drone(
    val manufacturer: String,
    val maxWindSpeedResistance: String,
    val model: String,
    val operatingTemperature: String
) {

    override fun toString(): String {
        return "$manufacturer $model"
    }
}