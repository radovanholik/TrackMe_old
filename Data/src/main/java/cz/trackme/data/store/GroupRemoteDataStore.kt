package cz.trackme.data.store

import cz.trackme.data.model.GroupEntity
import cz.trackme.data.repository.GroupDataStore
import cz.trackme.data.repository.GroupRemote
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