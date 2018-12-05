package cz.trackme.domain.interactor.group

import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.ObservableUseCase
import cz.trackme.domain.model.Group
import cz.trackme.domain.repository.GroupRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetGroup @Inject constructor(
        private val groupRepository: GroupRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<Group, GetGroup.Params>(postExecutionThread){

    override fun buildUseCaseObservable(params: Params?): Observable<Group> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        else if (params.groupId.isEmpty()) throw IllegalArgumentException("User ID can't be null!")

        return groupRepository.getGroup(params.groupId)
    }

    data class Params constructor(val groupId: String) {
        companion object {
            fun forGroup(groupId: String): Params {
                return Params(groupId)
            }
        }
    }
}