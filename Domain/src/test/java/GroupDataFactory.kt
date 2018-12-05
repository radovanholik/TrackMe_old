
import cz.trackme.domain.model.Group
import java.util.*

object GroupDataFactory {

    fun makeGroup(): Group {
        return Group(
                id = UtilsFactory.randomUuid(),
                name = UtilsFactory.randomUuid(),
                userIds = makeUserIds(3),
                createdAt = Random().nextLong()
        )
    }

    fun makeGroupList(count: Int): List<Group> {
        val groups = mutableListOf<Group>()
        repeat(count) {
            groups.add(makeGroup())
        }

        return groups
    }

    fun makeUserIds(count: Int): List<String> {
        val userIds = mutableListOf<String>()
        repeat(count) {
            userIds.add(UtilsFactory.randomUuid())
        }

        return userIds
    }
}