package cz.trackme.data.store

import cz.trackme.data.repository.UserDataStore
import javax.inject.Inject

open class UserDataStoreFactory @Inject constructor(
        private val userRemoteDataStore: UserRemoteDataStore) {

    open fun getDataStore() : UserDataStore {
        return userRemoteDataStore
    }
}