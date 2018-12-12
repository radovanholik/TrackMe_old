package cz.trackme.data.repository

import cz.trackme.data.model.LocationEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface LocationDataStore {

    fun addLocation(userId: String, location: LocationEntity): Completable

    fun getLocations(userId: String, limit: Int): Observable<List<LocationEntity>>
}