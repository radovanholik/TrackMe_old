package cz.radovanholik.data.mapper

import com.nhaarman.mockito_kotlin.mock
import cz.radovanholik.data.factory.UserFactory
import cz.radovanholik.data.model.UserEntity
import cz.trackme.domain.model.User
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class UserMapperTest {

    private var locationMapper = mock<LocationMapper>()
    private val mapper = UserMapper(locationMapper)

    @Test
    fun mapFromEntityMapsData() {
        val entity = UserFactory.makeUserEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
        assertEqualsLastKnownLocations_mapFromEntity(entity, model)
    }

    @Test
    fun mapToEntityMapsData() {
        val model = UserFactory.makeUser()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
        assertEqualsLastKnownLocations_mapToEntity(entity, model)
    }


    // helper methods
    private fun assertEqualData(entity: UserEntity,
                                model: User) {
        assertEquals(entity.id, model.id)
        assertEquals(entity.firstName, model.firstName)
        assertEquals(entity.lastName, model.lastName)
        assertEquals(entity.avatar, model.avatar)
        assertEquals(entity.phone, model.phone)
        assertEquals(entity.email, model.email)
        assertEquals(entity.groupIds, model.groupIds)
    }

    private fun assertEqualsLastKnownLocations_mapFromEntity(entity: UserEntity, model: User) {
        assertEquals(entity.lastKnownLocations?.map {
            mapper.locationMapper.mapFromEntity(it) }, model.lastKnownLocations )
    }

    private fun assertEqualsLastKnownLocations_mapToEntity(entity: UserEntity, model: User) {
        assertEquals(entity.lastKnownLocations, model.lastKnownLocations?.map {
            mapper.locationMapper.mapToEntity(it) } )
    }
}