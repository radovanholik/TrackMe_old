package cz.trackme.data

import cz.trackme.data.mapper.LocationMapper
import cz.trackme.data.store.LocationDataStoreFactory
import cz.trackme.domain.model.Location
import cz.trackme.domain.repository.LocationRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocationDataRepository @Inject constructor(
        private val mapper: LocationMapper,
        private val factory: LocationDataStoreFactory)
    : LocationRepository {

    override fun addLocation(userId: String, location: Location): Completable {
        val entityLocation = mapper.mapToEntity(location)
        return factory.getDataStore().addLocation(userId, entityLocation)
    }

    override fun getLocations(userId: String, limit: Int): Observable<List<Location>> {
        return factory.getDataStore().getLocations(userId, limit).map { entities ->
            entities.map {
                mapper.mapFromEntity(it)
            }
        }
    }
}