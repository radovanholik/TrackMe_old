package cz.trackme.remote.model

import java.util.*

data class LocationModel (
        override val id: String,
        val latitude: Double,
        val longitude: Double,
        val timestamp: Date
) : FirestoreModel()