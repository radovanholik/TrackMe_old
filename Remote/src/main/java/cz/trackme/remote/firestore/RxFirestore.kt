package cz.trackme.remote.firestore

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class RxFirestore<T> {

    private val TAG = RxFirestore::class.java.name

    val db = FirebaseFirestore.getInstance()

    fun getObservableForSingleDocument(colPath: String, docId: String, t: T, clazz: Class<T>)
            : Observable<T> {
        return getObservableForSingleDocument("$colPath/$docId", t, clazz)
    }

    // TODO - change Observer<T> to Single<T>
    fun getObservableForSingleDocument(docPath: String, t: T, clazz: Class<T>): Observable<T> {
        return Observable.create(object: ObservableOnSubscribe<T> {
            override fun subscribe(emitter: ObservableEmitter<T>) {
                val docRef = db.document(docPath)
                docRef.get()
                        .addOnSuccessListener { document ->
                            Log.d(TAG, document.toString())
                            val value: T? = document.toObject(clazz)
                            if (value == null) {
                                Log.d(TAG, "Document doesn't exist.  ${document}")
                                if (!emitter.isDisposed) emitter.onError(
                                        FirestoreRxDataCastException("Unable to cast Firestore " +
                                                "data response to ${clazz.simpleName}"))
                            }
                            else {
                                if (!emitter.isDisposed) emitter.onNext(value)
                            }
                        }
                        .addOnFailureListener { e ->
                            if (!emitter.isDisposed) {
                                emitter.onError(FirestoreRxDataException(e))
                            }
                        }
            }
        })
    }
}