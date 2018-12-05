package cz.trackme.domain.interactor.group

import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.CompletableUseCase
import cz.trackme.domain.model.Group
import cz.trackme.domain.repository.GroupRepository
import io.reactivex.Completable
import javax.inject.Inject

class SaveGroup @Inject constructor(
        private val groupRepository: GroupRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<SaveGroup.Params>(postExecutionThread){

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")

        return groupRepository.saveGroup(params.group)
    }

    data class Params constructor(val group: Group) {
        companion object {
            fun forGroup(group: Group): Params {
                return Params(group)
            }
        }
    }
}