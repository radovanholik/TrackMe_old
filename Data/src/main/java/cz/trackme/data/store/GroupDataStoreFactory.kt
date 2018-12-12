package cz.trackme.data.store

import cz.trackme.data.repository.GroupDataStore
import javax.inject.Inject

open class GroupDataStoreFactory @Inject constructor(
        private val groupRemoteDataStore: GroupRemoteDataStore) {

    open fun getDataStore() : GroupDataStore {
        return groupRemoteDataStore
    }
}