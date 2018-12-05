package location

import LocationDataFactory
import UtilsFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.location.GetLocations
import cz.trackme.domain.model.Location
import cz.trackme.domain.repository.LocationRepository
import io.reactivex.Observable
import org.junit.Test

class GetLocationsTest {

    var locationRepository = mock<LocationRepository>()
    var postExecutionThread = mock<PostExecutionThread>()
    var getLocations = GetLocations(locationRepository, postExecutionThread)
    private val limit = 3
    private val locations = LocationDataFactory.makeLocationList(limit)


    @Test
    fun getLocationsCompletes() {
        stubLocationRepositoryGetLocation(Observable.just(locations))

        val testObserver = getLocations.buildUseCaseObservable(
                GetLocations.Params.forLocation(UtilsFactory.randomUuid(), limit)).test()
        testObserver.assertComplete()
    }

    /**
     * Test that [GetLocations] throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getLocationsWithNoParamsThrowsException() {
        getLocations.buildUseCaseObservable()
    }

    /**
     * Test that [GetLocations] throws [IllegalArgumentException] in case User ID in params is empty.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getLocationsWithEmptyUserIdInParamsThrowsException() {
        getLocations.buildUseCaseObservable(GetLocations.Params.forLocation("", limit))
    }

    /**
     * Test that [GetLocations] throws [IllegalArgumentException] in case 'limit' in params is lower
     * or equal than 0.
     */
    @Test(expected = IllegalArgumentException::class)
    fun getLocationsWithNegativeLimitInParamsThrowsException() {
        getLocations.buildUseCaseObservable(GetLocations.Params.forLocation(
                UtilsFactory.randomUuid(), -3))
    }

    /**
     * Tests if the [LocationRepository.getLocations] is getting called.
     */
    @Test
    fun getLocationsCallsRepository() {
        stubLocationRepositoryGetLocation(Observable.just(locations))
        getLocations.buildUseCaseObservable(GetLocations.Params.forLocation(
                UtilsFactory.randomUuid(), limit)).test()
        verify(locationRepository).getLocations(any(), any())
    }

    /**
     * Tests if the [LocationRepository.getLocations] returns data.
     */
    @Test
    fun getGroupsReturnsData() {
        stubLocationRepositoryGetLocation(Observable.just(locations))
        val testObserver = getLocations.buildUseCaseObservable(
                GetLocations.Params.forLocation(UtilsFactory.randomUuid(), limit)).test()
        testObserver.assertValue(locations)
    }

    // stub methods
    private fun stubLocationRepositoryGetLocation(observable: Observable<List<Location>>) {
        whenever(locationRepository.getLocations(any(), any()))
                .thenReturn(observable)
    }
}