package cz.trackme.remote

import cz.trackme.data.model.LocationEntity
import cz.trackme.data.repository.LocationRemote
import cz.trackme.remote.firestore.location.LocationService
import cz.trackme.remote.mapper.LocationModelMapper
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocationRemoteImpl @Inject constructor(
        private val mapper: LocationModelMapper,
        private val locationService: LocationService
) : LocationRemote {

    override fun addLocation(userId: String, location: LocationEntity): Completable {
        return locationService.addLocation(userId, mapper.mapToModel(location))
    }

    override fun getLocations(userId: String, limit: Int): Observable<List<LocationEntity>> {
        return locationService.getLocations(userId, limit).map { models ->
            models.map {
                mapper.mapFromModel(it)
            }
        }
    }
}