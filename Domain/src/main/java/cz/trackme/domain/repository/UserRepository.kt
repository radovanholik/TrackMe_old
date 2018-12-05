package cz.trackme.domain.repository

import cz.trackme.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {

    fun saveUser(user: User): Completable

    fun getUser(userId: String): Observable<User>

    fun getUsers(userIds: List<String>) : Observable<List<User>>
}