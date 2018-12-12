package cz.radovanholik.data.store

import cz.radovanholik.data.model.LocationEntity
import cz.radovanholik.data.repository.LocationDataStore
import cz.radovanholik.data.repository.LocationRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class LocationRemoteDataStore @Inject constructor(private val locationRemote: LocationRemote)
    : LocationDataStore {

    override fun addLocation(userId: String, location: LocationEntity): Completable {
        return locationRemote.addLocation(userId, location)
    }

    override fun getLocations(userId: String, limit: Int): Observable<List<LocationEntity>> {
        return locationRemote.getLocations(userId, limit)
    }
}