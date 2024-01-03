package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.GalleryAlbumEntity
import ru.leisure.imgur.domain.models.GalleryAlbum

class GalleryAlbumConverter(
    private val mediaConverter: MediaConverter = MediaConverter(),
    private val uriConverter: UriConverter = UriConverter()
) {

    fun convert(source: GalleryAlbumEntity) =
        GalleryAlbum(
            id = source.id,
            title = source.title,
            link = uriConverter.convert(source.link),
            score = source.score,
            commentCount = source.commentCount,
            mediaCount = source.mediaCount,
            mediaList = mediaConverter.convert(source.mediaList)
        )

    fun convert(sourceList: List<GalleryAlbumEntity>) = sourceList.map { convert(it) }
}