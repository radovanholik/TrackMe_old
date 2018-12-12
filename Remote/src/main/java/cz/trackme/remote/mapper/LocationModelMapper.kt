package cz.trackme.remote.mapper

import cz.trackme.data.model.LocationEntity
import cz.trackme.remote.model.LocationModel
import javax.inject.Inject

class LocationModelMapper @Inject constructor(): ModelMapper<LocationModel, LocationEntity> {

    override fun mapFromModel(model: LocationModel): LocationEntity {
        return LocationEntity(
                uid = model.uid,
                latitude = model.latitude,
                longitude = model.longitude,
                timestamp = model.timestamp
        )
    }

}