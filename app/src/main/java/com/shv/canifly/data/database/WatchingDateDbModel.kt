package com.shv.canifly.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.shv.canifly.domain.entity.HourlyWeather
import java.time.LocalDateTime

@Entity(tableName = "watching_dates")
data class WatchingDateDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateTime: LocalDateTime,
    val location: LatLng,
    var flyStatus: Boolean,
    var isCompleted: Boolean,
    @Embedded
    val conditions: HourlyWeather
)
