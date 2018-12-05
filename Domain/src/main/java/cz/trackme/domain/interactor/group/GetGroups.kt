package cz.trackme.domain.interactor.group

import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.ObservableUseCase
import cz.trackme.domain.model.Group
import cz.trackme.domain.repository.GroupRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetGroups @Inject constructor(
        private val groupRepository: GroupRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Group>, GetGroups.Params>(postExecutionThread){

    override fun buildUseCaseObservable(params: Params?): Observable<List<Group>> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        else if (params.groupIds.isEmpty()) throw IllegalArgumentException("List of groups ids cannot be null.")

        return groupRepository.getGroups(params.groupIds)
    }

    data class Params constructor(val groupIds: List<String>){
        companion object {
            fun forGroups(groupIds: List<String>): Params {
                return Params(groupIds)
            }
        }
    }
}