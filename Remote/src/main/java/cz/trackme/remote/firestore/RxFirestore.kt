package cz.trackme.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observer

class RxFirestore<T> {

    val db = FirebaseFirestore.getInstance()

    fun getObservableForSingleEvent(t: T, clazz: Class<T>): Observer<T>? {
        // TODO
        return null
    }
}