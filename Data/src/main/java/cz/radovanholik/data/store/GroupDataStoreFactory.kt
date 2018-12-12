package cz.radovanholik.data.store

import cz.radovanholik.data.repository.GroupDataStore
import javax.inject.Inject

open class GroupDataStoreFactory @Inject constructor(
        private val groupRemoteDataStore: GroupRemoteDataStore) {

    open fun getDataStore() : GroupDataStore {
        return groupRemoteDataStore
    }
}