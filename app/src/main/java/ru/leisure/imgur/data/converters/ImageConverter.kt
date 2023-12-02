package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.ImageEntity
import ru.leisure.imgur.domain.models.Image

class ImageConverter {

    fun convert(source: ImageEntity) =
        Image(
            id = source.id,
            title = source.title.orEmpty(),
            description = source.description,
            type = source.type,
            name = source.name,
            section = source.section,
            link = source.link,
            gifv = source.gifv,
            mp4 = source.mp4
        )

    fun convert(sourceList: List<ImageEntity>) = sourceList.map { convert(it) }
}