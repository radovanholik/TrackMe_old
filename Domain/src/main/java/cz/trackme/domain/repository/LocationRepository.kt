package cz.trackme.domain.repository

import cz.trackme.domain.model.Location
import io.reactivex.Completable
import io.reactivex.Observable

interface LocationRepository {

    fun addLocation(userId: String, location: Location): Completable

    fun getLocations(userId: String, limit: Int): Observable<List<Location>>
}