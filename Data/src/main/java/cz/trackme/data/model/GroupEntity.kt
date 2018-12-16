package cz.trackme.data.model

import java.util.*

data class GroupEntity (
        val id: String,
        val name: String,
        val userIds: List<String>? = null,
        val createdAt: Date)