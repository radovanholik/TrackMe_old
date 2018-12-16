package cz.trackme.domain.model

import java.util.*

data class Location (
        val uid: String,
        val latitude: Double,
        val longitude: Double,
        val timestamp: Date
)