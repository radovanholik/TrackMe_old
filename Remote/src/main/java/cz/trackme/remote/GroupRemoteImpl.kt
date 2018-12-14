package cz.trackme.remote

import cz.trackme.data.model.GroupEntity
import cz.trackme.data.repository.GroupRemote
import io.reactivex.Completable
import io.reactivex.Observable

class GroupRemoteImpl : GroupRemote {

    override fun saveGroup(groupEntity: GroupEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroup(groupId: String): Observable<GroupEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroups(groupIds: List<String>): Observable<List<GroupEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}