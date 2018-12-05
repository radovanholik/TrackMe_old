package cz.radovanholik.data.mapper

import cz.radovanholik.data.model.UserEntity
import cz.trackme.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() : EntityMapper<UserEntity, User>{

    override fun mapFromEntity(entity: UserEntity): User {
        return User(
                id = entity.id,
                name = entity.name,
                avatar = entity.avatar,
                phone = entity.phone,
                email = entity.email,
                groupIds = entity.groupIds,
                lastKnownLocations = entity.lastKnownLocations)
    }

    override fun mapToEntity(domain: User): UserEntity {
        return UserEntity(
                id = domain.id,
                name = domain.name,
                avatar = domain.avatar,
                phone = domain.phone,
                email = domain.email,
                groupIds = domain.groupIds,
                lastKnownLocations = domain.lastKnownLocations)
    }
}