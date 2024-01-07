package ru.leisure.imgur.feature.base.data.converters

import ru.leisure.imgur.feature.base.data.models.TagEntity
import ru.leisure.imgur.feature.base.domain.models.Tag

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