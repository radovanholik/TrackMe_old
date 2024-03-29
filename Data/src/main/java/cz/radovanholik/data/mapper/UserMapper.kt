package cz.radovanholik.data.mapper

import cz.radovanholik.data.model.UserEntity
import cz.trackme.domain.model.User
import javax.inject.Inject

open class UserMapper @Inject constructor(val locationMapper: LocationMapper) : EntityMapper<UserEntity, User>{

    override fun mapFromEntity(entity: UserEntity): User {
        return User (
                id = entity.id,
                firstName = entity.firstName,
                lastName = entity.lastName,
                avatar = entity.avatar,
                phone = entity.phone,
                email = entity.email,
                groupIds = entity.groupIds,
                lastKnownLocations = entity.lastKnownLocations.map { locationMapper.mapFromEntity(it) })
    }

    override fun mapToEntity(domain: User): UserEntity {
        return UserEntity (
                id = domain.id,
                firstName = domain.firstName,
                lastName = domain.lastName,
                avatar = domain.avatar,
                phone = domain.phone,
                email = domain.email,
                groupIds = domain.groupIds,
                lastKnownLocations = domain.lastKnownLocations.map { locationMapper.mapToEntity(it) })
    }
}