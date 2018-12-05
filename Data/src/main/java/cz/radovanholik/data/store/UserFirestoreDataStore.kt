package cz.radovanholik.data.store

import cz.radovanholik.data.model.UserEntity
import cz.radovanholik.data.repository.UserDataStore
import cz.radovanholik.data.repository.UserFiretore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserFirestoreDataStore @Inject constructor(
        private val userFirestore: UserFiretore)
    : UserDataStore {

    override fun saveUser(user: UserEntity): Completable {
        return userFirestore.saveUser(user)
    }

    override fun getUser(userId: String): Observable<UserEntity> {
        return userFirestore.getUser(userId)
    }

    override fun getUsers(userIds: List<String>): Observable<List<UserEntity>> {
        return userFirestore.getUsers(userIds)
    }
}