package group

import GroupDataFactory
import UtilsFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.group.GetGroup
import cz.trackme.domain.model.Group
import cz.trackme.domain.repository.GroupRepository
import io.reactivex.Observable
import org.junit.Test

class GetGroupTest {

    var groupRepository = mock<GroupRepository>()
    var postExecutionThread = mock<PostExecutionThread>()
    var getGroup = GetGroup(groupRepository, postExecutionThread)
    var group = GroupDataFactory.makeGroup()

    /**
     * Tests that [GetGroup] completes.
     */
    @Test
    fun getGroupCompletes() {
        stubGroupRepositoryGetGroup(Observable.just(group))

        val params = GetGroup.Params.forGroup(UtilsFactory.randomUuid())
        val testObserver = getGroup.buildUseCaseObservable(params).test()
        testObserver.assertComplete()
    }

    /**
     * Tests that [GetGroup] throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getGroupWithNoParamsThrowsException() {
        getGroup.buildUseCaseObservable().test()
    }

    /**
     * Tests that [GetGroup] throws [IllegalArgumentException] in case Group ID in Params is an empty
     * string.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getGroupWithEmptyGroupIdThrowsException() {
        getGroup.buildUseCaseObservable(GetGroup.Params("")).test()
    }

    /**
     * The method tests if [GroupRepository.getGroup] is getting called.
     */
    @Test
    fun getUserCallsRepository() {
        stubGroupRepositoryGetGroup(Observable.just(group))

        val params = GetGroup.Params.forGroup(group.id)
        getGroup.buildUseCaseObservable(params).test()
        verify(groupRepository).getGroup(any())
    }

    /**
     * Tests if [GetGroup] returns some data.
     */
    @Test
    fun getUserReturnsData() {
        stubGroupRepositoryGetGroup(Observable.just(group))
        val testObserver = getGroup.buildUseCaseObservable(GetGroup.Params.forGroup(group.id)).test()
        testObserver.assertValue(group)
    }

    // stup methods
    private fun stubGroupRepositoryGetGroup(observable: Observable<Group>) {
        whenever(groupRepository.getGroup(any()))
                .thenReturn(observable)
    }
}