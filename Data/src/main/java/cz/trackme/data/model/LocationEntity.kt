package cz.trackme.data.model

import java.util.*

data class LocationEntity (
        val uid: String,
        val latitude: Double,
        val longitude: Double,
        val timestamp: Date
)