package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.GalleryTagsEntity
import ru.leisure.imgur.domain.models.GalleryTags

class GalleryTagsConverter(
    private val tagConverter: TagConverter = TagConverter(),
    private val topicConverter: TopicConverter = TopicConverter()
) {

    fun convert(source: GalleryTagsEntity) = GalleryTags(
        tags = tagConverter.convert(source.tags),
        featured = source.featured,
        topics = topicConverter.convert(source.topics)
    )
}