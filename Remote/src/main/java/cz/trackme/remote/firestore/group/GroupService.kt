package cz.trackme.remote.firestore.group

import cz.trackme.remote.model.GroupModel
import io.reactivex.Completable
import io.reactivex.Observable

interface GroupService {

    fun saveGroup(group: GroupModel): Completable

    fun getGroup(groupId: String): Observable<GroupModel>

    fun getGroups(groupIds: List<String>): Observable<List<GroupModel>>
}