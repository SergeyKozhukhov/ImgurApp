package ru.leisure.imgur.data.converters

import java.net.URI
import java.net.URISyntaxException

class UriConverter {

    fun convert(source: String?): URI? {
        if (source == null) return null
        return try {
            URI(source)
        } catch (e: URISyntaxException) {
            null
        }
    }
}