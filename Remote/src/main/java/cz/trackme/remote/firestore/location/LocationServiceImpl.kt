package cz.trackme.remote.firestore.location

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import cz.trackme.remote.firestore.FirestoreConstants
import cz.trackme.remote.firestore.FirestoreConstants.COLLECTION_LOCATIONS
import cz.trackme.remote.firestore.FirestoreConstants.COLLECTION_USERS
import cz.trackme.remote.firestore.RxFirestore
import cz.trackme.remote.model.LocationModel
import io.reactivex.Completable
import io.reactivex.Observable

class LocationServiceImpl : LocationService {

    override fun addLocation(userId: String, location: LocationModel): Completable {
        val docRef = getLocationRef(userId).document(location.id)
        return RxFirestore.setDocument(docRef, location)
    }

    override fun getLocations(userId: String, limit: Int): Observable<List<LocationModel>> {
        return RxFirestore.getObservableForDocsChangesInCollection(
                colReference = getLocationRef(userId),
                orderByField = FirestoreConstants.LOCATIONS_TIMESTAMP_FIELD,
                orderDirection = Query.Direction.DESCENDING,
                limit = limit,
                clazz = LocationModel::class.java
        )
    }

    /**
     * Gets Locations collection reference.
     * Current path in the firebase db is - users/{userId}/locations
     */
    private fun getLocationRef(userId: String): CollectionReference {
        return RxFirestore.getDb().collection("$COLLECTION_USERS/$userId/$COLLECTION_LOCATIONS")
    }
}