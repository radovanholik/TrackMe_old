package cz.trackme.domain.interactor.location

import cz.trackme.domain.Constants.GET_LOCATIONS_DEFAULT_LIMIT
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.ObservableUseCase
import cz.trackme.domain.model.Location
import cz.trackme.domain.repository.LocationRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetLocations @Inject constructor(
        private val locationRepository: LocationRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Location>, GetLocations.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<List<Location>> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        else if (params.userId.isEmpty()) throw IllegalArgumentException("UserID param can't be null!")
        else if (params.limit <= 0) throw IllegalArgumentException("Limit param has to be greater than 0!")

        return locationRepository.getLocations(params.userId, params.limit)
    }

    data class Params constructor(val userId: String, val limit: Int = GET_LOCATIONS_DEFAULT_LIMIT) {
        companion object {
            fun forLocation(userId: String, limit: Int = GET_LOCATIONS_DEFAULT_LIMIT): Params {
                return Params(userId, limit)
            }
        }
    }
}