package cz.trackme.remote.model

import java.util.*

data class LocationModel (
        override var id: String? = null,
        val latitude: Double,
        val longitude: Double,
        val timestamp: Date
) : FirestoreModel()