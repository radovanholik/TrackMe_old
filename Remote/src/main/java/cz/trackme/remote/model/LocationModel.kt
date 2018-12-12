package cz.trackme.remote.model

data class LocationModel (
        val uid: String,
        val latitude: Long,
        val longitude: Long,
        val timestamp: Long
)