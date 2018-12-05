package cz.trackme.domain.interactor.user

import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.CompletableUseCase
import cz.trackme.domain.model.User
import cz.trackme.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

open class SaveUser @Inject constructor(
        private val userRepository: UserRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<SaveUser.Params>(postExecutionThread){

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return userRepository.saveUser(params.user)
    }

    data class Params constructor(val user: User) {
        companion object {
            fun forUser(user: User): Params {
                return Params(user)
            }
        }
    }
}