package cz.trackme.remote.firestore.exception

class FirestoreRxDataException : Exception {

    constructor(errorMessage: String) : super(errorMessage)

    constructor(throwable: Throwable) : super(throwable)
}