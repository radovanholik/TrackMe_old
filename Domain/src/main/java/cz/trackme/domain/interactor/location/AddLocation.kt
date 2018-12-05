package cz.trackme.domain.interactor.location

import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.CompletableUseCase
import cz.trackme.domain.model.Location
import cz.trackme.domain.repository.LocationRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddLocation @Inject constructor(
        private val locationRepository: LocationRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<AddLocation.Params>(postExecutionThread){


    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        else if (params.userId.isEmpty()) throw IllegalArgumentException("Param userId can't be empty!")

        return locationRepository.addLocation(params.userId, params.location)
    }

    data class Params constructor(val userId: String, val location: Location) {
        companion object {
            fun forLocation(userId: String, location: Location): Params {
                return Params(userId, location)
            }
        }
    }
}