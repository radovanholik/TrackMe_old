package cz.trackme.domain.model

data class Location (
        val uid: String,
        val latitude: Long,
        val longitude: Long,
        val timestamp: Long
)