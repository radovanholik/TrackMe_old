package cz.trackme.domain.location

import cz.trackme.domain.LocationDataFactory
import cz.trackme.domain.UtilsFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import cz.trackme.domain.executor.PostExecutionThread
import cz.trackme.domain.interactor.location.AddLocation
import cz.trackme.domain.repository.LocationRepository
import io.reactivex.Completable
import org.junit.Test

class AddLocationTest {

    var locationRepository = mock<LocationRepository>()
    var postExecutionThread = mock<PostExecutionThread>()
    var addLocation = AddLocation(locationRepository, postExecutionThread)
    private val userId = UtilsFactory.randomUuid()
    private val location = LocationDataFactory.makeLocation()

    /**
     * Tests if [AddLocation] completes.
     */
    @Test
    fun addLocationCompletes() {
        stubLocationRepositoryAddLocation(Completable.complete())
        val testObserver = addLocation.buildUseCaseCompletable(
                AddLocation.Params.forLocation(userId, location)).test()
        testObserver.assertComplete()
    }

    /**
     * Test if the class throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun addLocationWithNoParamsThrowsException() {
        addLocation.buildUseCaseCompletable().test()
    }

    /**
     * Test if the class throws [IllegalArgumentException] in case Params are null.
     */
    @Test(expected = IllegalArgumentException::class)
    fun addLocationWithEmptyUserIdInParamsThrowsException() {
        val params = AddLocation.Params.forLocation("", any())
        addLocation.buildUseCaseCompletable(params).test()
    }

    @Test
    fun addLocationCallsRepository() {
        stubLocationRepositoryAddLocation(Completable.complete())

        val params = AddLocation.Params.forLocation(userId, location)
        addLocation.buildUseCaseCompletable(params).test()

        verify(locationRepository).addLocation(any(), any())
    }

    // stub methods
    private fun stubLocationRepositoryAddLocation(completable: Completable) {
        whenever(locationRepository.addLocation(any(), any()))
                .thenReturn(completable)
    }
}