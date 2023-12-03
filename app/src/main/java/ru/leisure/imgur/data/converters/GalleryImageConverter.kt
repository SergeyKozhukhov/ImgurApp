package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.GalleryImageEntity
import ru.leisure.imgur.domain.models.GalleryImage

class GalleryImageConverter {

    fun convert(source: GalleryImageEntity) =
        GalleryImage(
            id = source.id,
            title = source.title,
            type = source.type,
            link = source.link,
            gifv = source.gifv,
            mp4 = source.mp4,
            commentCount = source.commentCount,
            score = source.score,
        )

    fun convert(sourceList: List<GalleryImageEntity>) = sourceList.map { convert(it) }
}