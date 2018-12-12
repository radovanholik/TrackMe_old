package cz.trackme.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.data.factory.DataFactory
import cz.trackme.data.factory.LocationFactory
import cz.trackme.data.mapper.LocationMapper
import cz.trackme.data.model.LocationEntity
import cz.trackme.data.repository.LocationDataStore
import cz.trackme.data.store.LocationDataStoreFactory
import cz.trackme.domain.model.Location
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocationDataRepositoryTest {

    private val mapper = mock<LocationMapper>()
    private val store = mock<LocationDataStore>()
    private val factory = mock<LocationDataStoreFactory>()
    private val repository = LocationDataRepository(mapper, factory)

    private val entityLocation = LocationFactory.makeLocationEntity()
    private val location = LocationFactory.makeLocation()

    @Before
    fun setup() {
        stubFactory_getDataStore()
    }

    // START -> testing addLocation() method
    @Test
    fun addLocationCompletes() {
        stubMapper_mapToEntity(entityLocation)
        stubStore_addLocation(Completable.complete())
        val testObserver = repository.addLocation(DataFactory.randomString(), location)
                .test()

        testObserver.assertComplete()
    }
    // END - addLocation()

    // START -> testing getLocations()
    @Test
    fun getLocationsCompletes() {
        stubMapper_mapFromEntity(location)
        val entities = Observable.just(listOf(entityLocation))
        stubStore_getLocations(entities)
        val testObserver = repository.getLocations(DataFactory.randomString(), 1)
                .test()

        testObserver.assertComplete()
    }

    @Test
    fun getLocationsReturnsData() {
        stubMapper_mapFromEntity(location)
        stubStore_getLocations(Observable.just(listOf(entityLocation)))
        val testObserver = repository.getLocations(DataFactory.randomString(), 1)
                .test()

        testObserver.assertValue(listOf(location))
    }
    // END

    // stub methods
    private fun stubFactory_getDataStore() {
        whenever(factory.getDataStore())
                .thenReturn(store)
    }

    private fun stubStore_addLocation(completable: Completable) {
        whenever(store.addLocation(any(), any()))
                .thenReturn(completable)
    }

    private fun stubStore_getLocations(observable: Observable<List<LocationEntity>>) {
        whenever(store.getLocations(any(), any()))
                .thenReturn(observable)
    }

    private fun stubMapper_mapFromEntity(model: Location) {
        whenever(mapper.mapFromEntity(any()))
                .thenReturn(model)
    }

    private fun stubMapper_mapToEntity(entity: LocationEntity) {
        whenever(mapper.mapToEntity(any()))
                .thenReturn(entity)
    }
}