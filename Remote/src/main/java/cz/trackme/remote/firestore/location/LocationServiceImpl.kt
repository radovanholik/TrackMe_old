package cz.trackme.remote.firestore.location

import cz.trackme.remote.model.LocationModel
import io.reactivex.Completable
import io.reactivex.Observable

class LocationServiceImpl : LocationService {

    override fun addLocation(userId: String, location: LocationModel): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocations(userId: String, limit: Int): Observable<List<LocationModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}