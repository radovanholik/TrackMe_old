package cz.radovanholik.data.model

data class UserEntity (
        var id: String,
        val firstName: String,
        val lastName: String,
        val avatar: String,
        val phone: String,
        val email: String,
        val groupIds: List<String>,
        val lastKnownLocations: List<LocationEntity>
)