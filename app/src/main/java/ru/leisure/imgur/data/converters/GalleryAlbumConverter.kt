package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.GalleryAlbumEntity
import ru.leisure.imgur.domain.models.GalleryAlbum

class GalleryAlbumConverter(
    private val imageConverter: ImageConverter = ImageConverter()
) {

    fun convert(source: GalleryAlbumEntity) =
        GalleryAlbum(
            id = source.id,
            title = source.title,
            link = source.link,
            score = source.score,
            isAlbum = source.isAlbum,
            commentCount = source.commentCount,
            imagesCount = source.imagesCount,
            images = source.images?.let { imageConverter.convert(it) } ?: emptyList()
        )

    fun convert(sourceList: List<GalleryAlbumEntity>) = sourceList.map { convert(it) }
}