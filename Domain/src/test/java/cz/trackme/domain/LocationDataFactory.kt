package cz.trackme.domain

import cz.trackme.domain.model.Location
import java.util.*

object LocationDataFactory {

    fun makeLocation(): Location {
        return Location(
                uid = UtilsFactory.randomUuid(),
                latitude = Random().nextLong(),
                longitude = Random().nextLong(),
                timestamp = Random().nextLong()
        )
    }

    fun makeLocationList(count: Int): List<Location> {
        val locations = mutableListOf<Location>()
        repeat(count) {
            locations.add(makeLocation())
        }

        return locations
    }

}