package cz.radovanholik.data.factory

import cz.radovanholik.data.model.LocationEntity
import cz.trackme.domain.model.Location

object LocationFactory {

    fun makeEntityLocation(): LocationEntity {
        return LocationEntity(
                uid = DataFactory.randomString(),
                latitude = DataFactory.randomLong(),
                longitude = DataFactory.randomLong(),
                timestamp = DataFactory.randomLong()
        )
    }

    fun makeEntityLocations(size: Int) : List<LocationEntity> {
        val locations = mutableListOf<LocationEntity>()
        repeat(size) {
            locations.add(makeEntityLocation())
        }

        return locations
    }

    fun makeLocation(): Location {
        return Location(
                uid = DataFactory.randomString(),
                latitude = DataFactory.randomLong(),
                longitude = DataFactory.randomLong(),
                timestamp = DataFactory.randomLong()
        )
    }

    fun makeLocations(size: Int) : List<Location> {
        val locations = mutableListOf<Location>()
        repeat(size) {
            locations.add(makeLocation())
        }

        return locations
    }
}