package cz.trackme.remote.firestore.location

import cz.trackme.remote.model.LocationModel
import io.reactivex.Completable
import io.reactivex.Observable

interface LocationService {

    /**
     * This method adds a new user location obtained from a location service on the client.
     * @param userId User server ID
     * @param location A location model
     */
    fun addLocation(userId: String, location: LocationModel): Completable

    /**
     * This methods gets user locations.
     * @param userId User server ID
     * @param limit Defines how many records should be retrieved.
     */
    fun getLocations(userId: String, limit: Int): Observable<List<LocationModel>>
}