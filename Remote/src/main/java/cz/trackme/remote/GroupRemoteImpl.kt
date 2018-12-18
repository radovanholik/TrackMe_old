package cz.trackme.remote

import cz.trackme.data.model.GroupEntity
import cz.trackme.data.repository.GroupRemote
import cz.trackme.remote.firestore.group.GroupService
import cz.trackme.remote.mapper.GroupModelMapper
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class GroupRemoteImpl @Inject constructor(
        private val mapper: GroupModelMapper,
        private val groupService: GroupService)
    : GroupRemote {

    override fun saveGroup(groupEntity: GroupEntity): Completable {
        return groupService.saveGroup(mapper.mapToModel(groupEntity))
    }

    override fun getGroup(groupId: String): Observable<GroupEntity> {
        return groupService.getGroup(groupId).map { mapper.mapFromModel(it)}
    }

    override fun getGroups(groupIds: List<String>): Observable<List<GroupEntity>> {
        return groupService.getGroups(groupIds).map { models ->
            models.map {
                mapper.mapFromModel(it)
            }
        }
    }
}