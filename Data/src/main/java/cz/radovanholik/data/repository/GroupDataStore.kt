package cz.radovanholik.data.repository

import cz.radovanholik.data.model.GroupEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface GroupDataStore {

    fun saveGroup(groupEntity: GroupEntity): Completable

    fun getGroup(groupId: String): Observable<GroupEntity>

    fun getGroups(groupIds: List<String>): Observable<List<GroupEntity>>
}