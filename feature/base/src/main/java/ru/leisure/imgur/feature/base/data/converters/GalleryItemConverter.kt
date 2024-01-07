package ru.leisure.imgur.feature.base.data.converters

import ru.leisure.imgur.feature.base.data.models.GalleryAlbumEntity
import ru.leisure.imgur.feature.base.data.models.GalleryItemEntity
import ru.leisure.imgur.feature.base.data.models.GalleryMediaEntity

class GalleryItemConverter(
    private val galleryAlbumConverter: GalleryAlbumConverter = GalleryAlbumConverter(),
    private val galleryMediaConverter: GalleryMediaConverter = GalleryMediaConverter()
) {

    fun convert(source: GalleryItemEntity) =
        when (source) {
            is GalleryAlbumEntity -> galleryAlbumConverter.convert(source)
            is GalleryMediaEntity -> galleryMediaConverter.convert(source)
        }

    fun convert(sourceList: List<GalleryItemEntity>) = sourceList.map { convert(it) }
}