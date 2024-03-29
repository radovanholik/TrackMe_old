package cz.radovanholik.data.store

import cz.radovanholik.data.repository.UserDataStore
import javax.inject.Inject

open class UserDataStoreFactory @Inject constructor(
        private val userRemoteDataStore: UserRemoteDataStore) {

    open fun getDataStore() : UserDataStore {
        return userRemoteDataStore
    }
}