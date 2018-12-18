package cz.trackme.remote.model

import java.util.*

data class GroupModel (
        override val id: String,
        val name: String,
        val userIds: List<String>? = null,
        val createdAt: Date
) : FirestoreModel()