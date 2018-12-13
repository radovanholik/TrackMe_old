package cz.trackme.remote.model

data class UserModel (
    var id: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val avatar: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val groupIds: List<String>? = null,
    val lastKnownLocations: List<LocationModel>? = null
)