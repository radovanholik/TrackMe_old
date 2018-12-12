package cz.trackme.data.store

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test

class GroupDataStoreFactoryTest {

    private val remoteDataStore = mock<GroupRemoteDataStore>()
    private val factory = GroupDataStoreFactory(remoteDataStore)

    @Test
    fun getRemoteGroupRetrievesRemoteSource() {
        assert(factory.getDataStore() is GroupRemoteDataStore)
    }
}