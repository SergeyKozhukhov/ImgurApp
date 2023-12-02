package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.TagEntity
import ru.leisure.imgur.domain.models.Tag

class TagConverter {

    fun convert(source: TagEntity) = Tag(
        name = source.name,
        displayName = source.displayName,
        followers = source.followers,
        totalItems = source.totalItems,
        description = source.description
    )

    fun convert(sourceList: List<TagEntity>) = sourceList.map { convert(it) }
}