package cz.radovanholik.data.repository

import cz.radovanholik.data.model.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRemote {

    fun saveUser(user: UserEntity): Completable

    fun getUser(userId: String): Observable<UserEntity>

    fun getUsers(userIds: List<String>) : Observable<List<UserEntity>>
}