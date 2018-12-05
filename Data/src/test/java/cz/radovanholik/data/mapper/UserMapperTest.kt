package cz.radovanholik.data.mapper

import cz.radovanholik.data.factory.UserFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserMapperTest {

    private val mapper = UserMapper()

    @Test
    fun mapFromEntityMapsData() {
        val entity = UserFactory.makeUserEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }
}