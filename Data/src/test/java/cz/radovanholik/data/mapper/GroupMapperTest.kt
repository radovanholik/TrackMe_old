package cz.radovanholik.data.mapper

import cz.radovanholik.data.factory.GroupFactory
import cz.radovanholik.data.model.GroupEntity
import cz.trackme.domain.model.Group
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class GroupMapperTest {

    private val mapper = GroupMapper()

    @Test
    fun mapFromEntityMapsData() {
        val entity = GroupFactory.makeGroupEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test
    fun mapsToEntityMapsData() {
        val model = GroupFactory.makeGroup()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    // helper method
    private fun assertEqualData(entity: GroupEntity, model: Group) {
        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.userIds, model.userIds)
        assertEquals(entity.createdAt, model.createdAt)
    }
}