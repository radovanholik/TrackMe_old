package cz.trackme.domain.interactor.user

import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.ObservableUseCase
import cz.trackme.domain.model.User
import cz.trackme.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetUser @Inject constructor(
        private val userRepository: UserRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<User, GetUser.Params>(postExecutionThread){
    override fun buildUseCaseObservable(params: Params?): Observable<User> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        else if (params.userId.isEmpty()) throw IllegalArgumentException("User ID can't be null!")

        return userRepository.getUser(params.userId)
    }

    data class Params constructor(val userId: String) {
        companion object {
            fun forUser(userId: String): Params {
                return Params(userId)
            }
        }
    }
}