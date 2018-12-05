package cz.radovanholik.data.store

import cz.radovanholik.data.repository.UserDataStore
import javax.inject.Inject

class UserDataStoreFactory @Inject constructor(
        private val userFirestoreDataStore: UserFirestoreDataStore) {

    open fun getDataStore() : UserDataStore {
        return userFirestoreDataStore
    }
}