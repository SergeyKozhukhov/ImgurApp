package ru.leisure.imgur.feature.base.data.converters

import ru.leisure.imgur.feature.base.data.models.TopicEntity
import ru.leisure.imgur.feature.base.domain.models.Topic

class TopicConverter(
    private val galleryItemConverter: GalleryItemConverter = GalleryItemConverter()
) {

    fun convert(source: TopicEntity) = Topic(
        id = source.id,
        name = source.name,
        description = source.description,
        topPost = galleryItemConverter.convert(source.topPost)
    )

    fun convert(sourceList: List<TopicEntity>) = sourceList.map { convert(it) }
}