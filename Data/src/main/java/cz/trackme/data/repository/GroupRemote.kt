package cz.trackme.data.repository

import cz.trackme.data.model.GroupEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface GroupRemote {

    fun saveGroup(groupEntity: GroupEntity): Completable

    fun getGroup(groupId: String): Observable<GroupEntity>

    fun getGroups(groupIds: List<String>): Observable<List<GroupEntity>>
}