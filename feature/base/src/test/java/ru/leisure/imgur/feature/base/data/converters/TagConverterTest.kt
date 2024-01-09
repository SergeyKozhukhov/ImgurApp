package ru.leisure.imgur.feature.base.data.converters

import com.google.common.truth.Truth
import org.junit.Test
import ru.leisure.imgur.feature.base.data.models.TagEntity
import ru.leisure.imgur.feature.base.domain.models.Tag

internal class TagConverterTest {

    private val converter = TagConverter()

    @Test
    fun convert() {
        val output = converter.convert(tagEntity)
        Truth.assertThat(output).isEqualTo(tag)
    }

    @Test
    fun convertList() {
        val output = converter.convert(listOf(tagEntity, tagEntity, tagEntity))
        Truth.assertThat(output).isEqualTo(listOf(tag, tag, tag))
    }

    private companion object {
        const val name = "name"
        const val displayName = "displayName"
        const val followers = "followers"
        const val totalItems = "totalItems"
        const val description = "description"

        val tagEntity = TagEntity(
            name = name,
            displayName = displayName,
            followers = followers,
            totalItems = totalItems,
            description = description
        )

        val tag = Tag(
            name = name,
            displayName = displayName,
            followers = followers,
            totalItems = totalItems,
            description = description
        )

    }
}