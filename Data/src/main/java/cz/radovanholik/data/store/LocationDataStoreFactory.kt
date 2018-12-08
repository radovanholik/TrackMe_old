package cz.radovanholik.data.store

import cz.radovanholik.data.repository.LocationDataStore
import javax.inject.Inject

class LocationDataStoreFactory @Inject constructor(
        private val locationRemoteDataStore: LocationRemoteDataStore) {

    open fun getDataStore() : LocationDataStore {
        return locationRemoteDataStore
    }
}