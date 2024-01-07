package ru.leisure.imgur.feature.base.data.converters

import ru.leisure.imgur.feature.base.data.models.GalleryAlbumEntity
import ru.leisure.imgur.feature.base.domain.models.GalleryAlbum

class GalleryAlbumConverter(
    private val mediaConverter: MediaConverter = MediaConverter(),
    private val uriConverter: UriConverter = UriConverter()
) {

    fun convert(source: GalleryAlbumEntity): GalleryAlbum {
        val mediaList = mediaConverter.convert(source.mediaList)
        return GalleryAlbum(
            id = source.id,
            title = source.title,
            cover = mediaList.firstOrNull { it.id == source.cover } ?: mediaList.first(),
            coverId = source.cover,
            coverWidth = source.coverWidth,
            coverHeight = source.coverHeight,
            link = uriConverter.convert(source.link),
            score = source.score,
            commentCount = source.commentCount,
            mediaCount = source.mediaCount,
            mediaList = mediaList
        )
    }

    fun convert(sourceList: List<GalleryAlbumEntity>) = sourceList.map { convert(it) }
}