package ru.leisure.imgur.feature.base.data.converters

import ru.leisure.imgur.feature.base.data.models.MediaTagEntity
import ru.leisure.imgur.feature.base.domain.models.MediaTag

class MediaTagConverter(
    private val galleryItemConverter: GalleryItemConverter = GalleryItemConverter()
) {

    fun convert(source: MediaTagEntity) = MediaTag(
        name = source.name,
        followers = source.followers,
        totalItems = source.totalItems,
        description = source.description,
        items = galleryItemConverter.convert(source.items)
    )

    fun convert(sourceList: List<MediaTagEntity>) = sourceList.map { convert(it) }
}