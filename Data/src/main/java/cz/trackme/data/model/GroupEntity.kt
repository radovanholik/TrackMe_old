package cz.trackme.data.model

data class GroupEntity (
        val id: String,
        val name: String,
        val userIds: List<String>,
        val createdAt: Long)