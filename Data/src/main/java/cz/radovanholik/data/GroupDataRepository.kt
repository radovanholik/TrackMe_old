package cz.radovanholik.data

import cz.radovanholik.data.mapper.GroupMapper
import cz.radovanholik.data.store.GroupDataStoreFactory
import cz.trackme.domain.model.Group
import cz.trackme.domain.repository.GroupRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class GroupDataRepository @Inject constructor(
        private val mapper: GroupMapper,
        private val factory: GroupDataStoreFactory)
    : GroupRepository {

    override fun saveGroup(group: Group): Completable {
        return factory.getDataStore().saveGroup(mapper.mapToEntity(group))
    }

    override fun getGroup(groupId: String): Observable<Group> {
        return factory.getDataStore().getGroup(groupId).map { mapper.mapFromEntity(it) }
    }

    override fun getGroups(groupIds: List<String>): Observable<List<Group>> {
        return factory.getDataStore().getGroups(groupIds).map { entities ->
            entities.map {
                mapper.mapFromEntity(it)
            }
        }
    }
}