package cz.trackme.domain.user

import cz.trackme.domain.UserDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.user.GetUsers
import cz.trackme.domain.model.User
import cz.trackme.domain.repository.UserRepository
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUsersTest {

    var userRepository = mock<UserRepository>()
    var postExecutionThread = mock<PostExecutionThread>()
    var getUsers = GetUsers(userRepository, postExecutionThread)
    val users = UserDataFactory.makeUserList(3)
    val userIds = listOf(users[0].id, users[1].id, users[2].id)

    @Test
    fun getUsersCompletes() {
        stubUserRepositoryGetUsers(Observable.just(users))

        val testObserver = getUsers.buildUseCaseObservable(GetUsers.Params.forUsers(userIds))
                .test()
        testObserver.assertComplete()
    }

    /**
     * Test that [GetUsers] throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getUsersWithNoParamsThrowsException() {
        getUsers.buildUseCaseObservable()
    }

    /**
     * Test that [GetUsers] throws [IllegalArgumentException] in case the list in Params is empty.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getUsersWithEmptyUsersIdsThrowsException() {
        getUsers.buildUseCaseObservable(GetUsers.Params(listOf()))
    }

    /**
     * Tests if the [UserRepository.getUsers] is getting called.
     */
    @Test
    fun getUsersCallsRepository() {
        stubUserRepositoryGetUsers(Observable.just(users))
        getUsers.buildUseCaseObservable(GetUsers.Params.forUsers(userIds)).test()
        verify(userRepository).getUsers(any())
    }

    /**
     * Tests if the [UserRepository.getUsers] returns data.
     */
    @Test
    fun getUsersReturnsData() {
        stubUserRepositoryGetUsers(Observable.just(users))
        val testObserver = getUsers.buildUseCaseObservable(GetUsers.Params.forUsers(userIds)).test()
        testObserver.assertValue(users)
    }

    // stub methods
    private fun stubUserRepositoryGetUsers(observable: Observable<List<User>>) {
        whenever(userRepository.getUsers(any()))
                .thenReturn(observable)
    }

}