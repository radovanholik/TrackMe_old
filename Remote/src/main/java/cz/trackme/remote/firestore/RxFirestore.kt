package cz.trackme.remote.firestore

import android.util.Log
import com.google.firebase.firestore.*
import cz.trackme.remote.firestore.exception.FirestoreRxDataCastException
import cz.trackme.remote.firestore.exception.FirestoreRxDataException
import cz.trackme.remote.model.FirestoreModel
import io.reactivex.Completable
import io.reactivex.Observable

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

    fun <T> getObservableDocument(docReference: DocumentReference, clazz: Class<T>): Observable<T> {
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

    // TODO - convert it to Single
    fun <T> getObservableDocumentsByFieldValue(colReference: CollectionReference, fieldName: String,
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
     * The method adds a new document. If the pojo class doesn't have set an ID,
     * then the document ID is created first, updated in [pojo] and then saved
     * in Firestore.
     */
    fun addDocument(colReference: CollectionReference, pojo: FirestoreModel) : Completable {
        return Completable.create { emitter ->
            val newDocRef = colReference.document()
            pojo.id = newDocRef.id

            newDocRef.set(pojo)
                    .addOnSuccessListener { if (!emitter.isDisposed) emitter.onComplete() }
                    .addOnFailureListener {
                        if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(it))
                    }
        }
    }

    /**
     * The method sets a POJO class to the specific document. There is no merging strategy applied.
     * So, data are always overwritten.
     */
    fun setDocument(docReference: DocumentReference, pojo: Any): Completable {
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