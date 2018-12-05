package cz.trackme.domain.interactor.user

import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.ObservableUseCase
import cz.trackme.domain.model.User
import cz.trackme.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetUsers @Inject constructor(
        private val userRepository: UserRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<User>, GetUsers.Params>(postExecutionThread){


    override fun buildUseCaseObservable(params: Params?): Observable<List<User>> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        else if (params.userIds.isEmpty()) throw IllegalArgumentException("List of users' ids cannot be null.")

        return userRepository.getUsers(params.userIds)
    }


    data class Params constructor(val userIds: List<String>) {
        companion object {
            fun forUsers(userIds: List<String>): Params {
                return Params(userIds)
            }
        }
    }

}