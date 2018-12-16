package cz.trackme.remote.mapper

import cz.trackme.data.model.UserEntity
import cz.trackme.remote.model.UserModel
import javax.inject.Inject

class UserModelMapper @Inject constructor(val locationMapper: LocationModelMapper): ModelMapper<UserModel, UserEntity> {

    override fun mapFromModel(model: UserModel): UserEntity {
        return UserEntity(
                id = model.id!!,
                firstName = model.firstName,
                lastName = model.lastName,
                avatar = model.avatar,
                phone = model.phone,
                email = model.email,
                groupIds = model.groupIds,
                lastKnownLocations = model.lastKnownLocations?.map { locationMapper.mapFromModel(it) }
        )
    }

    override fun mapToModel(entity: UserEntity): UserModel {
        return UserModel(
                id = entity.id,
                firstName = entity.firstName,
                lastName = entity.lastName,
                avatar = entity.avatar,
                phone = entity.phone,
                email = entity.email,
                groupIds = entity.groupIds,
                lastKnownLocations = entity.lastKnownLocations?.map { locationMapper.mapToModel(it) }
        )
    }
}