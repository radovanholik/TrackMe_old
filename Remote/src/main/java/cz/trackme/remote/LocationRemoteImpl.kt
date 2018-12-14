package cz.trackme.remote

import cz.trackme.data.model.LocationEntity
import cz.trackme.data.repository.LocationRemote
import io.reactivex.Completable
import io.reactivex.Observable

class LocationRemoteImpl : LocationRemote {

    override fun addLocation(userId: String, location: LocationEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocations(userId: String, limit: Int): Observable<List<LocationEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}