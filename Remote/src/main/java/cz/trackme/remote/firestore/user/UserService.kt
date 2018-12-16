package cz.trackme.remote.firestore.user

import com.google.firebase.firestore.DocumentReference
import cz.trackme.remote.firestore.FirestoreConstants
import cz.trackme.remote.model.LocationModel
import cz.trackme.remote.model.UserModel
import io.reactivex.Completable
import io.reactivex.Observable

interface UserService {

    /**
     * The method sets User values. Replaces object in firestore if there is already some at the
     * specific document path (users/id).
     */
    fun saveUser(user: UserModel): Completable

    /**
     * The methods updates Group IDs in the user profile and returns Completable
     */
    fun updateUserGroupIds(userId: String, groupIds: List<String>): Completable

    /**
     * Returns a new observable of a user.
     */
    fun getUser(userId: String): Observable<UserModel>

    /**
     * Returns a new observable of list of users.
     */
    fun getUsers(userIds: List<String>): Observable<List<UserModel>>

    /**
     * @return This method returns [Pair] that consists of document reference and fields to update.
     * In this case, fields to update means [FirestoreConstants.USER_LAST_KNOWN_LOCATIONS_FIELD] and
     * list of last known locations.
     */
    fun getLatestLocationValuesToUpdate(userId: String, locations: List<LocationModel>): Pair<DocumentReference, Map<String, Any>>
}