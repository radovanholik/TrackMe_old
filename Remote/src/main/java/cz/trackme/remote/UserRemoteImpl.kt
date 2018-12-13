package cz.trackme.remote

import cz.trackme.data.model.UserEntity
import cz.trackme.data.repository.UserRemote
import cz.trackme.remote.firestore.UserFirestoreService
import cz.trackme.remote.mapper.UserModelMapper
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
        private val mapper: UserModelMapper,
        private val userFirestoreService: UserFirestoreService)
    : UserRemote {

    override fun saveUser(user: UserEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(userId: String): Observable<UserEntity> {
        return userFirestoreService.getUser(userId).map { mapper.mapFromModel(it) }
    }

    override fun getUsers(userIds: List<String>): Observable<List<UserEntity>> {
        return userFirestoreService.getUsers(userIds).map { models ->
            models.map {
                mapper.mapFromModel(it)
            }
        }
    }
}