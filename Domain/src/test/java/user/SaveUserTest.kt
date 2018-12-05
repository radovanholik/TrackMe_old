package user

import UserDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.user.SaveUser
import cz.trackme.domain.repository.UserRepository
import io.reactivex.Completable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class SaveUserTest {

    var userRepository = mock<UserRepository>()
    var postExecutionThread = mock<PostExecutionThread>()

    var saveUser = SaveUser(userRepository, postExecutionThread)

    @Test
    fun saveUserCompletes() {
        stubUserRepositorySaveUser(Completable.complete())

        val testObserver = saveUser.buildUseCaseCompletable(
                SaveUser.Params.forUser(UserDataFactory.makeUser())
        ).test()

        testObserver.assertComplete()
    }

    /**
     * Test if the class throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun saveUserWithNoParamsThrowsException() {
        saveUser.buildUseCaseCompletable().test()
    }

    @Test
    fun saveUserCallsRepository() {
        stubUserRepositorySaveUser(Completable.complete())

        val user = UserDataFactory.makeUser()
        val params = SaveUser.Params.forUser(user)
        saveUser.buildUseCaseCompletable(params).test()

        verify(userRepository).saveUser(any())
    }

    // stup methods
    private fun stubUserRepositorySaveUser(completable: Completable) {
        whenever(userRepository.saveUser(any()))
                .thenReturn(completable)
    }
}