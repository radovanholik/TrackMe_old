package cz.radovanholik.data.mapper

import cz.radovanholik.data.model.LocationEntity
import cz.trackme.domain.model.Location
import javax.inject.Inject

open class LocationMapper @Inject constructor() : EntityMapper<LocationEntity, Location> {

    override fun mapFromEntity(entity: LocationEntity): Location {
        return Location (
                uid = entity.uid,
                latitude = entity.latitude,
                longitude = entity.longitude,
                timestamp = entity.timestamp
        )
    }

    override fun mapToEntity(domain: Location): LocationEntity {
        return LocationEntity(
                uid = domain.uid,
                latitude = domain.latitude,
                longitude = domain.longitude,
                timestamp = domain.timestamp
        )
    }
}