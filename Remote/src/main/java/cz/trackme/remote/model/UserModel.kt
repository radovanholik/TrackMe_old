package cz.trackme.remote.model

data class UserModel (
    var id: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val phone: String,
    val email: String,
    val groupIds: List<String>,
    val lastKnownLocations: List<LocationModel>
)