package cz.trackme.remote.firestore

import android.util.Log
import com.google.firebase.firestore.*
import cz.trackme.remote.firestore.exception.FirestoreRxDataCastException
import cz.trackme.remote.firestore.exception.FirestoreRxDataException
import cz.trackme.remote.model.FirestoreModel
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Contains a bunch of helper methods to communicate with Firebase database.
 */
object RxFirestore {

    private val TAG = RxFirestore::class.java.name

    fun getDb() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // TODO - change Observer<T> to Single<T>
    fun <T> getObservableDocumentForSingleEvent(docReference: DocumentReference, clazz: Class<T>): Observable<T> {
        return Observable.create { emitter ->
            docReference.get()
                    .addOnSuccessListener { document ->
                        Log.d(TAG, document.toString())
                        val value: T? = document.toObject(clazz)
                        if (value == null) {
                            Log.d(TAG, "Document doesn't exist.  ${document}")
                            if (!emitter.isDisposed) {
                                emitter.onError(
                                        FirestoreRxDataCastException("Unable to cast Firestore " +
                                                "data response to ${clazz.simpleName}"))
                            }
                        } else {
                            if (!emitter.isDisposed) emitter.onNext(value)
                        }
                    }
                    .addOnFailureListener { e ->
                        if (!emitter.isDisposed) {
                            emitter.onError(FirestoreRxDataException(e))
                        }
                    }
        }
    }

    /**
     * @return Returns a new observable object that listens for document changes.
     */
    fun <T : FirestoreModel> getObservableDocument(docReference: DocumentReference, clazz: Class<T>): Observable<T> {
        return Observable.create { emitter ->
            val listener = docReference.addSnapshotListener(EventListener<DocumentSnapshot> {
                snapshot, e ->

                if (e != null) {
                    if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(
                            "Listen failed for ${clazz.simpleName}."))
                    return@EventListener
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Document data: $snapshot")
                    val value: T? = snapshot.toObject(clazz)

                    if (value == null) {
                        if (!emitter.isDisposed) {
                            emitter.onError(FirestoreRxDataCastException("Unable to cast " +
                                    "Firestore data response to ${clazz.simpleName}"))
                        }
                    } else {
                        if (!emitter.isDisposed) emitter.onNext(value)
                    }


                } else {
                    if (!emitter.isDisposed) {
                        emitter.onError(FirestoreRxDataException("Document data: null for " +
                                clazz.simpleName))
                    }
                }
            })


            emitter.setCancellable { listener.remove() }
        }
    }

    /**
     * This method gets an observable list for listening changes in the given collection.
     * Listens for "added", "modified" and "removed" documents within the query.
     *
     * @param colReference A collection reference.
     * @param orderByField Represents a field name which can be used for ordering.
     * @param orderDirection Represents the rule of ordering. Default value is [Query.Direction.ASCENDING]
     * @param limit Represents a limit of records.
     * @param clazz A class to map from firestore object.
     *
     */
    fun <T : FirestoreModel> getObservableForDocsChangesInCollection (
            colReference: CollectionReference, orderByField: String? = null,
            orderDirection: Query.Direction = Query.Direction.ASCENDING, limit: Int, clazz: Class<T>): Observable<List<T>> {

        return Observable.create { emitter ->
            colReference.limit(limit.toLong())
            orderByField?.let { fieldName ->
                colReference.orderBy(fieldName, orderDirection)
            }

            val dataList = mutableListOf<T>()

            val listener = colReference
                    .addSnapshotListener { querySnapshot, e ->

                        // in case some error
                        if (e != null && !emitter.isDisposed) emitter.onError(e)

                        // ok
                        querySnapshot?.let { snapshot ->
                            for (dc in snapshot.documentChanges) {
                                when (dc.type) {
                                    DocumentChange.Type.ADDED -> dataList.add(dc.document.toObject(clazz))

                                    DocumentChange.Type.MODIFIED -> {
                                        val obj = dc.document.toObject(clazz)
                                        val index = dataList.indexOfFirst { it.id == obj.id }
                                        if (index > -1) dataList[index] = obj
                                    }

                                    DocumentChange.Type.REMOVED -> {
                                        val obj = dc.document.toObject(clazz)
                                        val index = dataList.indexOfFirst { it.id == obj.id }
                                        dataList.removeAt(index)
                                    }
                                }
                            }

                            if (!emitter.isDisposed) emitter.onNext(dataList)
                        }
            }

            // remote the listener when disposing
            emitter.setCancellable { listener.remove() }
        }
    }

    // TODO - convert it to Single
    fun <T : FirestoreModel> getObservableDocumentsByFieldValue(colReference: CollectionReference, fieldName: String,
                                               values: List<String>, clazz: Class<T>)
            : Observable<List<T>> {

        return Observable.create { emitter ->

            val results = mutableListOf<T>()

            values.forEach { value ->
                colReference.whereEqualTo(fieldName, value)
            }

            colReference.get()
                    .addOnSuccessListener {documents ->
                        documents.forEach { document ->
                            val item: T = document.toObject(clazz)
                            results.add(item)
                        }

                        if (!emitter.isDisposed) emitter.onNext(results)
                    }
                    .addOnFailureListener { exception ->
                        if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(exception))
                    }
        }
    }

    /**
     * The method sets a POJO class to the specific document. There is no merging strategy applied.
     * So, data are always overwritten.
     */
    fun <T: FirestoreModel> setDocument(docReference: DocumentReference, pojo: T): Completable {
        return Completable.create{ emitter ->
            docReference.set(pojo)
                    .addOnSuccessListener { if (!emitter.isDisposed) emitter.onComplete() }
                    .addOnFailureListener {
                        if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(it))
                    }
        }
    }

    /**
     * The method updates document values for given fields. Merge option applied.
     * @param data Represents a set of data to update. Key = field name, Value = any object to update.
     */
    fun updateSpecificFieldValues(docReference: DocumentReference, data: Map<String, Any>)
        : Completable {

        return Completable.create { emitter ->
            docReference.set(data, SetOptions.merge())
                    .addOnSuccessListener { if (!emitter.isDisposed) emitter.onComplete() }
                    .addOnFailureListener {
                        if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(it))
                    }
        }
    }

    /**
     * This methods executes multiple write operations as a single batch. A batch of writes
     * completes atomically and can write to multiple documents. Batched writes execute even when
     * the user's device is offline.
     *
     * Follow external url:
     * *https://firebase.google.com/docs/firestore/manage-data/transactions*
     *
     * @param data Key - doc reference, Value - map of field updates
     * (Key - field name, Value - new field value)
     */
    private fun updateDocumentValuesWithinBatch(data: Map<DocumentReference, Map<String, Any>>): Completable {
        return Completable.create { emitter ->
            // Get a new write batch
            val batch = getDb().batch()

            // Iterate through all Document References
            data.forEach { (docRef, docData) ->
                // Update field values for the given document
                batch.update(docRef, docData)
            }

            // Commit the batch
            batch.commit()
                    .addOnCompleteListener {
                        if (!emitter.isDisposed) emitter.onComplete()
                    }
                    .addOnFailureListener {
                        if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(it))
                    }
        }
    }
}