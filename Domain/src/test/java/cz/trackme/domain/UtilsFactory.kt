package cz.trackme.domain

object UtilsFactory {

    fun randomUuid(): String {
        return java.util.UUID.randomUUID().toString()
    }
}