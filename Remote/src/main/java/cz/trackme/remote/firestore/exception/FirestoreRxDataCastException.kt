package cz.trackme.remote.firestore.exception

class FirestoreRxDataCastException : Exception {

    constructor()

    constructor(detailMessage: String) : super(detailMessage)

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable)

    constructor(throwable: Throwable) : super(throwable)
}