package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.TopicEntity
import ru.leisure.imgur.domain.models.Topic

class TopicConverter(
    private val galleryAlbumConverter: GalleryAlbumConverter = GalleryAlbumConverter()
) {

    fun convert(source: TopicEntity) = Topic(
        id = source.id,
        name = source.name,
        description = source.description,
        topPost = galleryAlbumConverter.convert(source.topPost)
    )

    fun convert(sourceList: List<TopicEntity>) = sourceList.map { convert(it) }
}