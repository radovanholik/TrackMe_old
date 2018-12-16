package cz.trackme.domain.model

import java.util.*

data class Group(
        val id: String,
        val name: String,
        val userIds: List<String>? = null,
        val createdAt: Date)