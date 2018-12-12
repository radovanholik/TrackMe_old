package cz.radovanholik.data.repository

import cz.radovanholik.data.model.LocationEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface LocationRemote {

    fun addLocation(userId: String, location: LocationEntity): Completable

    fun getLocations(userId: String, limit: Int): Observable<List<LocationEntity>>
}