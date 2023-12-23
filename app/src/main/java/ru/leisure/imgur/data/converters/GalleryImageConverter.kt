package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.GalleryImageEntity
import ru.leisure.imgur.domain.models.GalleryImage

class GalleryImageConverter(
    private val uriConverter: UriConverter = UriConverter()
) {

    fun convert(source: GalleryImageEntity) =
        GalleryImage(
            id = source.id,
            title = source.title,
            type = source.type,
            link = uriConverter.convert(source.link),
            gifv = uriConverter.convert(source.gifv),
            mp4 = uriConverter.convert(source.mp4),
            commentCount = source.commentCount,
            score = source.score,
        )

    fun convert(sourceList: List<GalleryImageEntity>) = sourceList.map { convert(it) }
}