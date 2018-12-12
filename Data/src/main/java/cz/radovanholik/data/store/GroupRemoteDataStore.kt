package cz.radovanholik.data.store

import cz.radovanholik.data.model.GroupEntity
import cz.radovanholik.data.repository.GroupDataStore
import cz.radovanholik.data.repository.GroupRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class GroupRemoteDataStore @Inject constructor(
        private val groupRemote: GroupRemote)
    : GroupDataStore {

    override fun saveGroup(groupEntity: GroupEntity): Completable {
        return groupRemote.saveGroup(groupEntity)
    }

    override fun getGroup(groupId: String): Observable<GroupEntity> {
        return groupRemote.getGroup(groupId)
    }

    override fun getGroups(groupIds: List<String>): Observable<List<GroupEntity>> {
        return groupRemote.getGroups(groupIds)
    }


}