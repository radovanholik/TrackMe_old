package user

import UserDataFactory
import UtilsFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.user.GetUser
import cz.trackme.domain.model.User
import cz.trackme.domain.repository.UserRepository
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserTest {

    var userRepository = mock<UserRepository>()
    var postExecutionThread = mock<PostExecutionThread>()
    var getUser = GetUser(userRepository, postExecutionThread)

    /**
     * Tests that [GetUser] completes.
     */
    @Test
    fun getUserCompletes() {
        val observable = Observable.just(UserDataFactory.makeUser())
        stubUserRepositoryGetUser(observable)

        val params = GetUser.Params.forUser(UtilsFactory.randomUuid())
        val testObserver = getUser.buildUseCaseObservable(params).test()
        testObserver.assertComplete()
    }

    /**
     * Tests that [GetUser] throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getUserWithNoParamsThrowsException() {
        getUser.buildUseCaseObservable().test()
    }

    /**
     * Tests that [GetUser] throws [IllegalArgumentException] in case User ID in Params is an empty
     * string.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getUserWithEmptyUserIdThrowsException() {
        getUser.buildUseCaseObservable(GetUser.Params("")).test()
    }

    /**
     * The method tests if [UserRepository.getUser] is getting called.
     */
    @Test
    fun getUserCallsRepository() {
        stubUserRepositoryGetUser(Observable.just(UserDataFactory.makeUser()))

        val params = GetUser.Params.forUser(UtilsFactory.randomUuid())
        getUser.buildUseCaseObservable(params).test()
        verify(userRepository).getUser(any())
    }

    @Test
    fun getUserReturnsData() {
        val user = UserDataFactory.makeUser()
        stubUserRepositoryGetUser(Observable.just(user))
        val testObserver = getUser.buildUseCaseObservable(GetUser.Params.forUser(user.id)).test()
        testObserver.assertValue(user)
    }

    // Stub methods
    private fun stubUserRepositoryGetUser(observable: Observable<User>) {
        whenever(userRepository.getUser(any()))
                .thenReturn(observable)
    }
}