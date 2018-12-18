package cz.trackme.remote.mapper

import com.nhaarman.mockito_kotlin.mock
import cz.trackme.data.model.UserEntity
import cz.trackme.remote.model.UserModel
import org.junit.Test

class UserModelMapperTest {

    private var locationMapper = mock<LocationModelMapper>()
    private val mapper = UserModelMapper(locationMapper)

    @Test
    fun mapFromModelMapsData() {
        TODO()
    }

    @Test
    fun mapToModelMapsData() {
        TODO()
    }

    private fun assertEqualData(model: UserModel, entity: UserEntity) {
        TODO()
    }
}