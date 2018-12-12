package cz.trackme.data.factory

import cz.trackme.data.model.GroupEntity
import cz.trackme.domain.model.Group

object GroupFactory {

    fun makeGroupEntity(): GroupEntity {
        return GroupEntity (
                id = DataFactory.randomString(),
                name = DataFactory.randomString(),
                userIds = DataFactory.randomListOfStrings(3),
                createdAt = DataFactory.randomLong()
        )
    }

    fun makeGroup(): Group {
        return Group (
                id = DataFactory.randomString(),
                name = DataFactory.randomString(),
                userIds = DataFactory.randomListOfStrings(3),
                createdAt = DataFactory.randomLong()
        )
    }
}