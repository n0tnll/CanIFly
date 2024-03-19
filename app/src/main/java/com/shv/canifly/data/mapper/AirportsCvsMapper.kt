package com.shv.canifly.data.mapper

import com.shv.canifly.domain.entity.Airport
import com.shv.canifly.domain.entity.AirportType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.StringReader

suspend fun String?.toAirportsList(): List<Airport> {
    return CoroutineScope(Dispatchers.IO).async {
        val airports = mutableListOf<Airport>()
        this@toAirportsList?.let {
            val stringReader = StringReader(it)
            val parser = CSVParser(
                stringReader,
                CSVFormat.DEFAULT
            )
            for (zone in parser.drop(HEADER_TABLE_INDEX)) {
                if (zone[2] == CLOSED_AIRPORT) {
                    continue
                }
                airports.add(
                    Airport(
                        id = zone[0],
                        ident = zone[1],
                        type = AirportType.typeFromString(zone[2]),
                        name = zone[3],
                        latitude = zone[4].toDouble(),
                        longitude = zone[5].toDouble(),
                        approximateRadius = AirportType.getRadius(zone[2])
                    )
                )
            }
        }
        airports
    }.await()
}

private const val CLOSED_AIRPORT = "closed"
private const val HEADER_TABLE_INDEX = 1