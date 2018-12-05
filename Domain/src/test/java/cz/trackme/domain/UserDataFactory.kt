package cz.trackme.domain

import cz.trackme.domain.model.User

object UserDataFactory {

    fun makeUser(): User {
        return User(
                id = UtilsFactory.randomUuid(),
                firstName = "First name",
                lastName = "Last name",
                avatar = UtilsFactory.randomUuid(),
                phone = UtilsFactory.randomUuid(),
                email = UtilsFactory.randomUuid(),
                groupIds = makeGroupIds(3),
                lastKnownLocations = LocationDataFactory.makeLocationList(3))
    }

    fun makeUserList(count: Int): List<User> {
        val users = mutableListOf<User>()
        repeat(count) {
            users.add(makeUser())
        }

        return users
    }

    fun makeGroupIds(count: Int) : List<String> {
        val groupIds = mutableListOf<String>()
        repeat(count) {
            groupIds.add(UtilsFactory.randomUuid())
        }

        return groupIds
    }
}