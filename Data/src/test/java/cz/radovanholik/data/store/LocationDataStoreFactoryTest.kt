package cz.radovanholik.data.store

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LocationDataStoreFactoryTest {

    private val remoteDataStore = mock<LocationRemoteDataStore>()
    private val factory = LocationDataStoreFactory(remoteDataStore)

    @Test
    fun getRemoteLocationRetrievesRemoteSource() {
        assert(factory.getDataStore() is LocationRemoteDataStore)
    }
}