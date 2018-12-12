package cz.trackme.data.store

import cz.trackme.data.model.UserEntity
import cz.trackme.data.repository.UserDataStore
import cz.trackme.data.repository.UserRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class UserRemoteDataStore @Inject constructor(
        private val userRemote: UserRemote)
    : UserDataStore {

    override fun saveUser(user: UserEntity): Completable {
        return userRemote.saveUser(user)
    }

    override fun getUser(userId: String): Observable<UserEntity> {
        return userRemote.getUser(userId)
    }

    override fun getUsers(userIds: List<String>): Observable<List<UserEntity>> {
        return userRemote.getUsers(userIds)
    }
}