package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.GalleryAlbumEntity
import ru.leisure.imgur.data.models.GalleryImageEntity
import ru.leisure.imgur.data.models.GalleryItemEntity

class GalleryItemConverter(
    private val galleryAlbumConverter: GalleryAlbumConverter = GalleryAlbumConverter(),
    private val galleryImageConverter: GalleryImageConverter = GalleryImageConverter()
) {

    fun convert(source: GalleryItemEntity) =
        when (source) {
            is GalleryAlbumEntity -> galleryAlbumConverter.convert(source)
            is GalleryImageEntity -> galleryImageConverter.convert(source)
        }

    fun convert(sourceList: List<GalleryItemEntity>) = sourceList.map { convert(it) }
}