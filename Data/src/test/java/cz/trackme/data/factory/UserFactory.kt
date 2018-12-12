package cz.trackme.data.factory

import cz.trackme.data.model.UserEntity
import cz.trackme.domain.model.User

object UserFactory {

    fun makeUserEntity(): UserEntity {
        return UserEntity(
                id = DataFactory.randomString(),
                firstName = DataFactory.randomString(),
                lastName = DataFactory.randomString(),
                avatar = DataFactory.randomString(),
                phone = DataFactory.randomString(),
                email = DataFactory.randomString(),
                groupIds = listOf(DataFactory.randomString(), DataFactory.randomString()),
                lastKnownLocations = LocationFactory.makeLocationEntity(3)
        )
    }

    fun makeUser() : User {
        return User (
                id = DataFactory.randomString(),
                firstName = DataFactory.randomString(),
                lastName = DataFactory.randomString(),
                avatar = DataFactory.randomString(),
                phone = DataFactory.randomString(),
                email = DataFactory.randomString(),
                groupIds = listOf(DataFactory.randomString(), DataFactory.randomString()),
                lastKnownLocations = LocationFactory.makeLocations(3)
        )
    }
}