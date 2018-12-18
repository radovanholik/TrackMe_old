package cz.trackme.remote.firestore.group

import cz.trackme.remote.firestore.FirestoreConstants
import cz.trackme.remote.firestore.FirestoreConstants.COLLECTION_GROUPS
import cz.trackme.remote.firestore.RxFirestore
import cz.trackme.remote.model.GroupModel
import io.reactivex.Completable
import io.reactivex.Observable

class GroupServiceImpl : GroupService {

    private val groupsCollectionReference = RxFirestore.getDb().collection(COLLECTION_GROUPS)

    override fun saveGroup(group: GroupModel): Completable {
        val docRef = groupsCollectionReference.document(group.id)
        return RxFirestore.setDocument(docRef, group)
    }

    override fun getGroup(groupId: String): Observable<GroupModel> {
        val docRef = groupsCollectionReference.document(groupId)
        return RxFirestore.getObservableDocument(docRef, GroupModel::class.java)
    }

    override fun getGroups(groupIds: List<String>): Observable<List<GroupModel>> {
        return RxFirestore.getObservableDocumentsByFieldValue(
                colReference = groupsCollectionReference,
                fieldName = FirestoreConstants.GROUP_ID_FIELD,
                values = groupIds,
                clazz = GroupModel::class.java)
    }
}