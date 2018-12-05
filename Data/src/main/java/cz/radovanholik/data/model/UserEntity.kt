package cz.radovanholik.data.model

import cz.trackme.domain.model.Location
import cz.trackme.domain.model.Name

data class UserEntity (
        var id: String,
        val name: Name,
        val avatar: String,
        val phone: String,
        val email: String,
        val groupIds: List<String>,
        val lastKnownLocations: List<Location>
)