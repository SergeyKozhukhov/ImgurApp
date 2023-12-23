package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.GalleryMediaEntity
import ru.leisure.imgur.domain.models.GalleryMedia

class GalleryMediaConverter(
    private val mediaConverter: MediaConverter = MediaConverter()
) {

    fun convert(source: GalleryMediaEntity) =
        GalleryMedia(
            media = mediaConverter.convert(source),
            commentCount = source.commentCount,
            score = source.score,
        )

    fun convert(sourceList: List<GalleryMediaEntity>) = sourceList.map { convert(it) }
}