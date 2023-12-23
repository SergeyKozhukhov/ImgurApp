package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.ImageEntity
import ru.leisure.imgur.domain.models.Image

class ImageConverter(
    private val uriConverter: UriConverter = UriConverter()
) {

    fun convert(source: ImageEntity) =
        Image(
            id = source.id,
            title = source.title.orEmpty(),
            description = source.description,
            type = source.type,
            isAnimated = source.isAnimated,
            name = source.name,
            section = source.section,
            link = uriConverter.convert(source.link),
            gifv = uriConverter.convert(source.gifv),
            mp4 = uriConverter.convert(source.mp4),
            hasSound = source.hasSound
        )

    fun convert(sourceList: List<ImageEntity>) = sourceList.map { convert(it) }
}