package cz.trackme.domain.model

data class User(
        var id: String,
        val firstName: String? = null,
        val lastName: String? = null,
        val avatar: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val groupIds: List<String>? = null,
        val lastKnownLocations: List<Location>? = null
)