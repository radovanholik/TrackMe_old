package cz.trackme.domain.model

data class Group(
        val id: String,
        val name: String,
        val userIds: List<String>? = null,
        val createdAt: Long)