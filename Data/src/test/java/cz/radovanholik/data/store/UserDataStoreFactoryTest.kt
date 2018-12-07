package cz.radovanholik.data.store

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDataStoreFactoryTest {

    private val remoteStore = mock<UserRemoteDataStore>()
    private val factory = UserDataStoreFactory(remoteStore)

    @Test
    fun getRemoteUserRetrievesRemoteSource() {
        assert(factory.getDataStore() is UserRemoteDataStore)
    }
}