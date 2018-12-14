package cz.trackme.remote.firestore

import android.util.Log
import com.google.firebase.firestore.*
import cz.trackme.remote.firestore.exception.FirestoreRxDataCastException
import cz.trackme.remote.firestore.exception.FirestoreRxDataException
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

            values.forEachIndexed { index, value ->
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
     * The method saves a POJO class to the specific document. There is no merging strategy applied.
     */
    fun saveDocument(docReference: DocumentReference, pojo: Any): Completable {
        return Completable.create{ emitter ->
            docReference.set(pojo, SetOptions.merge())
                    .addOnSuccessListener { if (!emitter.isDisposed) emitter.onComplete() }
                    .addOnFailureListener {
                        if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(it))
                    }
        }
    }

    /**
     * The method updates document values ([V]) for given fields ([K])
     */
    fun <K, V> updateSpecificFieldValues(docReference: DocumentReference, data: Map<K, V>)
        : Completable {
        return Completable.create { emitter ->
            docReference.set(data, SetOptions.merge())
                    .addOnSuccessListener { if (!emitter.isDisposed) emitter.onComplete() }
                    .addOnFailureListener {
                        if (!emitter.isDisposed) emitter.onError(FirestoreRxDataException(it))
                    }
        }
    }
}