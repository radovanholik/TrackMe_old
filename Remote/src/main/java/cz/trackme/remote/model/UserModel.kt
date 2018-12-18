package cz.trackme.remote.model

import java.util.*

data class UserModel (
        override val id: String,
        val firstName: String? = null,
        val lastName: String? = null,
        val avatar: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val timestamp: Date,
        val groupIds: List<String>? = null,
        val lastKnownLocations: List<LocationModel>? = null
) : FirestoreModel()