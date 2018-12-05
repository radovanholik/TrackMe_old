package cz.trackme.domain.model

data class User(
        var id: String,
        val name: Name,
        val avatar: String,
        val phone: String,
        val email: String,
        val groupIds: List<String>,
        val lastKnownLocations: List<Location>
)