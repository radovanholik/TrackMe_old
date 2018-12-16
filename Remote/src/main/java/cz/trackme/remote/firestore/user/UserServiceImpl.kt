package cz.trackme.remote.firestore.user

import com.google.firebase.firestore.DocumentReference
import cz.trackme.remote.firestore.FirestoreConstants
import cz.trackme.remote.firestore.FirestoreConstants.COLLECTION_USERS
import cz.trackme.remote.firestore.FirestoreConstants.USER_GROUP_IDS
import cz.trackme.remote.firestore.FirestoreConstants.USER_ID_FIELD
import cz.trackme.remote.firestore.RxFirestore
import cz.trackme.remote.model.LocationModel
import cz.trackme.remote.model.UserModel
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    private val usersCollectionReference = RxFirestore.getDb().collection(COLLECTION_USERS)

    override fun saveUser(user: UserModel): Completable {
        val docRef = usersCollectionReference.document()
        return RxFirestore.setDocument(docRef, user)
    }

    override fun updateUserGroupIds(userId: String, groupIds: List<String>): Completable {
        val docRef = usersCollectionReference.document(userId)
        val data = hashMapOf(USER_GROUP_IDS to groupIds)

        return RxFirestore.updateSpecificFieldValues(
                docReference = docRef, data = data
        )
    }

    override fun getUser(userId: String): Observable<UserModel> {
        val docRef = usersCollectionReference.document(userId)
        return RxFirestore.getObservableDocumentForSingleEvent(docRef, UserModel::class.java)
    }

    override fun getUsers(userIds: List<String>): Observable<List<UserModel>> {
        return RxFirestore.getObservableDocumentsByFieldValue(
                colReference = usersCollectionReference,
                fieldName = USER_ID_FIELD,
                values = userIds,
                clazz = UserModel::class.java)
    }

    override fun getLatestLocationValuesToUpdate(userId: String, locations: List<LocationModel>): Pair<DocumentReference, Map<String, Any>> {
        val docRef = usersCollectionReference.document(userId)
        val data = mapOf (FirestoreConstants.USER_LAST_KNOWN_LOCATIONS_FIELD to locations)

        return Pair(docRef, data)
    }
}