package cz.trackme.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.data.factory.DataFactory
import cz.trackme.data.factory.UserFactory
import cz.trackme.data.mapper.UserMapper
import cz.trackme.data.model.UserEntity
import cz.trackme.data.repository.UserDataStore
import cz.trackme.data.store.UserDataStoreFactory
import cz.trackme.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDataRepositoryTest {

    private val mapper = mock<UserMapper>()
    private val store = mock<UserDataStore>()
    private val factory = mock<UserDataStoreFactory>()
    private val repository = UserDataRepository(mapper, factory)
    private val entityUser = UserFactory.makeUserEntity()
    private val user = UserFactory.makeUser()

    @Before
    fun setup() {
        stubFactory_getDataStore()
    }

    // START -> testing saveUser() methods
    @Test
    fun saveUserCompletes() {
        stubStore_saveUser(Completable.complete())
        stubMapper_mapToEntity(entityUser)

        val testObserver = repository.saveUser(user)
        testObserver.test().assertComplete()
    }
    // END -> testing saveUser() methods

    // START -> testing getUser() method
    @Test
    fun getUserCompletes() {
        stubStore_getUser(Observable.just(entityUser))
        stubMapper_mapFromEntity(user)

        val testObserver = repository.getUser(user.id).test()
        testObserver.assertComplete()
    }

    @Test
    fun getUserReturnsData() {
        stubStore_getUser(Observable.just(entityUser))
        stubMapper_mapFromEntity(user)

        val testObserver = repository.getUser(user.id).test()
        testObserver.assertValue(user)
    }
    // END

    // START -> testing getUsers() method
    @Test
    fun getUsersCompletes() {
        stubStore_getUsers(Observable.just(listOf(entityUser)))
        stubMapper_mapFromEntity(user)

        val testObserver = repository.getUsers(listOf(DataFactory.randomString()))
                .test()
        testObserver.assertComplete()
    }

    @Test
    fun getUsersReturnsData() {
        stubStore_getUsers(Observable.just(listOf(entityUser)))
        stubMapper_mapFromEntity(user)

        val testObserver = repository.getUsers(listOf(DataFactory.randomString()))
                .test()
        testObserver.assertValue(listOf(user))
    }
    // END

    // stub methods
    private fun stubFactory_getDataStore() {
        whenever(factory.getDataStore())
                .thenReturn(store)
    }

    private fun stubStore_saveUser(completable: Completable) {
        whenever(store.saveUser(any()))
                .thenReturn(completable)
    }

    private fun stubStore_getUser(observable: Observable<UserEntity>) {
        whenever(store.getUser(any()))
                .thenReturn(observable)
    }

    private fun stubStore_getUsers(observable: Observable<List<UserEntity>>) {
        whenever(store.getUsers(any()))
                .thenReturn(observable)
    }

    private fun stubMapper_mapFromEntity(model: User) {
        whenever(mapper.mapFromEntity(any()))
                .thenReturn(model)
    }

    private fun stubMapper_mapToEntity(entity: UserEntity) {
        whenever(mapper.mapToEntity(any()))
                .thenReturn(entity)
    }
}