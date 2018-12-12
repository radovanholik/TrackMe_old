package cz.trackme.data

import cz.trackme.data.mapper.UserMapper
import cz.trackme.data.store.UserDataStoreFactory
import cz.trackme.domain.model.User
import cz.trackme.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserDataRepository @Inject constructor(
        private val mapper: UserMapper,
        private val factory: UserDataStoreFactory )
    : UserRepository {

    override fun saveUser(user: User): Completable {
        return factory.getDataStore().saveUser(mapper.mapToEntity(user))
    }

    override fun getUser(userId: String): Observable<User> {
        return factory.getDataStore().getUser(userId).map { mapper.mapFromEntity(it) }
    }

    override fun getUsers(userIds: List<String>): Observable<List<User>> {
        return factory.getDataStore().getUsers(userIds).map { entities ->
            entities.map {
                mapper.mapFromEntity(it)
            }
        }
    }
}