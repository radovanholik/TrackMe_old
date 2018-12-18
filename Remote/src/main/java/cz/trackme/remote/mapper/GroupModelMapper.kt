package cz.trackme.remote.mapper

import cz.trackme.data.model.GroupEntity
import cz.trackme.remote.model.GroupModel
import javax.inject.Inject

class GroupModelMapper @Inject constructor(): ModelMapper<GroupModel, GroupEntity> {

    override fun mapFromModel(model: GroupModel): GroupEntity {
        return GroupEntity(
                id = model.id,
                name = model.name,
                userIds = model.userIds,
                createdAt = model.createdAt
        )
    }

    override fun mapToModel(entity: GroupEntity): GroupModel {
        return GroupModel(
                id = entity.id,
                name = entity.name,
                userIds = entity.userIds,
                createdAt = entity.createdAt
        )
    }
}