package cz.trackme.data.store

import cz.trackme.data.repository.LocationDataStore
import javax.inject.Inject

open class LocationDataStoreFactory @Inject constructor(
        private val locationRemoteDataStore: LocationRemoteDataStore) {

    open fun getDataStore() : LocationDataStore {
        return locationRemoteDataStore
    }
}