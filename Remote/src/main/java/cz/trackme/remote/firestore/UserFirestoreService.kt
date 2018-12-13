package cz.trackme.remote.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import cz.trackme.remote.firestore.FirestoreConstants.COLLECTION_USERS
import cz.trackme.remote.firestore.FirestoreConstants.USER_ID_FIELD
import cz.trackme.remote.model.UserModel
import io.reactivex.Observable
import javax.inject.Inject

class UserFirestoreService @Inject constructor() {

    private val usersCollectionReference = RxFirestore.getDb().collection(COLLECTION_USERS)
    private var setUserRef : DocumentReference? = null
    private var getUserRef : DocumentReference? = null
    private var getUsersRef : CollectionReference? = null



    fun getUser(userId: String): Observable<UserModel> {
        getUserRef = usersCollectionReference.document(userId)
        return RxFirestore.getObservableDocumentForSingleEvent(getUserRef!!, UserModel::class.java)
    }

    fun getUsers(userIds: List<String>): Observable<List<UserModel>> {
        return RxFirestore.getObservableDocumentsByFieldValue (
                colReference = usersCollectionReference,
                fieldName = USER_ID_FIELD,
                values = userIds,
                clazz = UserModel::class.java)
    }
}