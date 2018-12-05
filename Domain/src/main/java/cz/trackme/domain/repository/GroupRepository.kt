package cz.trackme.domain.repository

import cz.trackme.domain.model.Group
import io.reactivex.Completable
import io.reactivex.Observable

interface GroupRepository {

    fun saveGroup(group: Group): Completable

    fun getGroup(groupId: String): Observable<Group>

    fun getGroups(groupIds: List<String>): Observable<List<Group>>
}