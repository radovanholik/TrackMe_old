package cz.trackme.remote.firestore

import com.google.firebase.firestore.DocumentReference
import cz.trackme.remote.firestore.FirestoreConstants.COLLECTION_USERS
import cz.trackme.remote.firestore.FirestoreConstants.USER_GROUP_IDS
import cz.trackme.remote.firestore.FirestoreConstants.USER_ID_FIELD
import cz.trackme.remote.firestore.FirestoreConstants.USER_LAST_KNOWN_LOCATIONS_FIELD
import cz.trackme.remote.model.LocationModel
import cz.trackme.remote.model.UserModel
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserFirestoreService @Inject constructor() {

    private val usersCollectionReference = RxFirestore.getDb().collection(COLLECTION_USERS)
    private var getUserRef : DocumentReference? = null

    /**
     * The method saves User. Replaces object in firestore if there is already some at the
     * specific document path (users/id).
     */
    fun saveUser(user: UserModel): Completable {
        val docRef = usersCollectionReference.document(user.id)
        return RxFirestore.saveDocument(docRef, user)
    }

    /**
     * The methods updates Group IDs in the user profile and returns Completable
     */
    fun updateUserGroupIds(userId: String, groupIds: List<String>): Completable {
        val docRef = usersCollectionReference.document(userId)
        val data = hashMapOf(USER_GROUP_IDS to groupIds)

        return RxFirestore.updateSpecificFieldValues(
                docReference = docRef, data = data
        )
    }

    /**
     * The methods updates last known locations in the user profile and returns Completable
     */
    fun updateUserLatestLocation(userId: String, locations: List<LocationModel>): Completable {
        val docRef = usersCollectionReference.document(userId)
        val data = hashMapOf (USER_LAST_KNOWN_LOCATIONS_FIELD to locations)

        return RxFirestore.updateSpecificFieldValues(
                docReference = docRef, data = data
        )
    }

    /**
     * Returns a new observable of a user.
     */
    fun getUser(userId: String): Observable<UserModel> {
        getUserRef = usersCollectionReference.document(userId)
        return RxFirestore.getObservableDocumentForSingleEvent(getUserRef!!, UserModel::class.java)
    }

    /**
     * Returns a new observable of list of users.
     */
    fun getUsers(userIds: List<String>): Observable<List<UserModel>> {
        return RxFirestore.getObservableDocumentsByFieldValue (
                colReference = usersCollectionReference,
                fieldName = USER_ID_FIELD,
                values = userIds,
                clazz = UserModel::class.java)
    }
}