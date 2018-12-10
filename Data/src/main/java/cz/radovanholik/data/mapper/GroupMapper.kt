package cz.radovanholik.data.mapper

import cz.radovanholik.data.model.GroupEntity
import cz.trackme.domain.model.Group
import javax.inject.Inject

open class GroupMapper @Inject constructor() : EntityMapper<GroupEntity, Group> {

    override fun mapFromEntity(entity: GroupEntity): Group {
        return Group(
                id = entity.id,
                name = entity.name,
                userIds = entity.userIds,
                createdAt = entity.createdAt
        )
    }

    override fun mapToEntity(domain: Group): GroupEntity {
        return GroupEntity(
                id = domain.id,
                name = domain.name,
                userIds = domain.userIds,
                createdAt = domain.createdAt
        )
    }
}