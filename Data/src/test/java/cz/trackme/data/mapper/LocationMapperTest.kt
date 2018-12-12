package cz.trackme.data.mapper

import cz.trackme.data.factory.LocationFactory
import cz.trackme.data.model.LocationEntity
import cz.trackme.domain.model.Location
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class LocationMapperTest {

    private val mapper = LocationMapper()

    @Test
    fun mapFromEntityMapsData() {
        val entity = LocationFactory.makeLocationEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test
    fun mapToEntityMapsData() {
        val model = LocationFactory.makeLocation()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    // helper method
    private fun assertEqualData(entity: LocationEntity,
                        model: Location) {

        assertEquals(entity.uid, model.uid)
        assertEquals(entity.latitude, model.longitude)
        assertEquals(entity.longitude, model.longitude)
        assertEquals(entity.timestamp, model.timestamp)
    }
}