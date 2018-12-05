package cz.trackme.domain.group

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.GroupDataFactory
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.group.GetGroups
import cz.trackme.domain.model.Group
import cz.trackme.domain.repository.GroupRepository
import io.reactivex.Observable
import org.junit.Test

class GetGroupsTest {

    var groupRepository = mock<GroupRepository>()
    var postExecutionThread = mock<PostExecutionThread>()
    var getGroups = GetGroups(groupRepository, postExecutionThread)
    private val groups = GroupDataFactory.makeGroupList(3)
    private val groupIds = listOf(groups[0].id, groups[1].id, groups[2].id)

    @Test
    fun getGroupsCompletes() {
        stubGroupRepositoryGetGroups(Observable.just(groups))

        val testObserver = getGroups.buildUseCaseObservable(GetGroups.Params.forGroups(groupIds))
                .test()
        testObserver.assertComplete()
    }

    /**
     * Test that [GetGroups] throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getGroupsWithNoParamsThrowsException() {
        getGroups.buildUseCaseObservable()
    }

    /**
     * Test that [GetGroups] throws [IllegalArgumentException] in case the list in Params is empty.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getGroupsWithEmptyGroupsIdsThrowsException() {
        getGroups.buildUseCaseObservable(GetGroups.Params.forGroups(listOf()))
    }

    /**
     * Tests if the [GroupRepository.getGroups] is getting called.
     */
    @Test
    fun getGroupsCallsRepository() {
        stubGroupRepositoryGetGroups(Observable.just(groups))
        getGroups.buildUseCaseObservable(GetGroups.Params.forGroups(groupIds)).test()
        verify(groupRepository).getGroups(any())
    }

    /**
     * Tests if the [GroupRepository.getGroups] returns data.
     */
    @Test
    fun getGroupsReturnsData() {
        stubGroupRepositoryGetGroups(Observable.just(groups))
        val testObserver = getGroups.buildUseCaseObservable(GetGroups.Params.forGroups(groupIds))
                .test()
        testObserver.assertValue(groups)
    }

    // stub methods
    private fun stubGroupRepositoryGetGroups(observable: Observable<List<Group>>) {
        whenever(groupRepository.getGroups(any()))
                .thenReturn(observable)
    }
}