package cz.trackme.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.data.factory.DataFactory
import cz.trackme.data.factory.GroupFactory
import cz.trackme.data.mapper.GroupMapper
import cz.trackme.data.model.GroupEntity
import cz.trackme.data.repository.GroupDataStore
import cz.trackme.data.store.GroupDataStoreFactory
import cz.trackme.domain.model.Group
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GroupDataRepositoryTest {

    private val mapper = mock<GroupMapper>()
    private val store = mock<GroupDataStore>()
    private val factory = mock<GroupDataStoreFactory>()
    private val repository = GroupDataRepository(mapper, factory)

    private val entityGroup = GroupFactory.makeGroupEntity()
    private val group = GroupFactory.makeGroup()

    @Before
    fun setup() {
        stubFactory_getDataStore()
    }

    // START - testing of saveGroup() method
    @Test
    fun saveGroupCompletes() {
        stubStore_saveGroup(Completable.complete())
        stubMapper_mapToEntity(entityGroup)

        val testObserver = repository.saveGroup(group)
                .test()
        testObserver.assertComplete()
    }
    // END

    // START - testing of getGroup() method
    @Test
    fun getGroupCompletes() {
        stubMapper_mapFromEntity(group)
        stubStore_getGroup(Observable.just(entityGroup))

        val testObserver = repository.getGroup(DataFactory.randomString())
                .test()

        testObserver.assertComplete()
    }

    @Test
    fun getGroupReturnsData() {
        stubMapper_mapFromEntity(group)
        stubStore_getGroup(Observable.just(entityGroup))

        val testObserver = repository.getGroup(DataFactory.randomString())
                .test()

        testObserver.assertValue(group)
    }
    // END

    // START - testing of getGroups() method
    @Test
    fun getGroupsCompletes() {
        stubMapper_mapFromEntity(group)
        stubStore_getGroups(Observable.just(listOf(entityGroup)))

        val testObserver = repository.getGroups(listOf(DataFactory.randomString()))
                .test()

        testObserver.assertComplete()
    }

    @Test
    fun getGroupsReturnsValue() {
        stubMapper_mapFromEntity(group)
        stubStore_getGroups(Observable.just(listOf(entityGroup)))

        val testObserver = repository.getGroups(listOf(DataFactory.randomString()))
                .test()

        testObserver.assertValue(listOf(group))
    }
    // END

    // stub methods
    private fun stubFactory_getDataStore() {
        whenever(factory.getDataStore())
                .thenReturn(store)
    }

    private fun stubStore_saveGroup(completable: Completable) {
        whenever(store.saveGroup(any()))
                .thenReturn(completable)
    }

    private fun stubStore_getGroup(observable: Observable<GroupEntity>) {
        whenever(store.getGroup(any()))
                .thenReturn(observable)
    }

    private fun stubStore_getGroups(observable: Observable<List<GroupEntity>>) {
        whenever(store.getGroups(any()))
                .thenReturn(observable)
    }

    private fun stubMapper_mapToEntity(entity: GroupEntity) {
        whenever(mapper.mapToEntity(any()))
                .thenReturn(entity)
    }

    private fun stubMapper_mapFromEntity(domain: Group) {
        whenever(mapper.mapFromEntity(any()))
                .thenReturn(domain)
    }
}