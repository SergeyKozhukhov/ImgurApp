package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.ImageEntity
import ru.leisure.imgur.domain.Image

class ImageConverter {

    fun convert(source: ImageEntity) =
        Image(
            id = source.id,
            title = source.title,
            description = source.description,
            name = source.name,
            link = source.link
        )

    fun convert(sourceList: List<ImageEntity>) = sourceList.map { convert(it) }
}