package cz.trackme.domain.group

import cz.trackme.domain.GroupDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.group.SaveGroup
import cz.trackme.domain.interactor.user.SaveUser
import cz.trackme.domain.repository.GroupRepository
import io.reactivex.Completable
import org.junit.Test

class SaveGroupTest {

    var groupRepository = mock<GroupRepository>()
    var postExecutionThread = mock<PostExecutionThread>()
    var saveGroup = SaveGroup(groupRepository, postExecutionThread)

    /**
     * Tests if [SaveUser] completes.
     */
    @Test
    fun saveUserCompletes() {
        stubGroupRepositorySaveGroup(Completable.complete())

        val testObserver = saveGroup.buildUseCaseCompletable(
                SaveGroup.Params.forGroup(GroupDataFactory.makeGroup())).test()

        testObserver.assertComplete()
    }

    /**
     * Tests if the class throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun saveGroupWithNoParamsThrowsException() {
        saveGroup.buildUseCaseCompletable().test()
    }

    /**
     * Tests calling [GroupRepository.getGroup]
     */
    @Test
    fun saveGroupCallsRepository() {
        stubGroupRepositorySaveGroup(Completable.complete())

        val group = GroupDataFactory.makeGroup()
        val params = SaveGroup.Params.forGroup(group)
        saveGroup.buildUseCaseCompletable(params).test()

        verify(groupRepository).saveGroup(group)
    }

    // stub methods
    private fun stubGroupRepositorySaveGroup(completable: Completable) {
        whenever(groupRepository.saveGroup(any()))
                .thenReturn(completable)
    }
}