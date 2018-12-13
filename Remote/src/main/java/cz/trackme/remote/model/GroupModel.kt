package cz.trackme.remote.model

data class GroupModel (
        val id: String,
        val name: String,
        val userIds: List<String>? = null,
        val createdAt: Long
)