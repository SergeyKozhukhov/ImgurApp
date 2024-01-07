package ru.leisure.imgur.feature.base.data.converters

import ru.leisure.imgur.feature.base.data.models.GalleryMediaEntity
import ru.leisure.imgur.feature.base.data.models.MediaEntity
import ru.leisure.imgur.feature.base.domain.models.Media

class MediaConverter(
    private val uriConverter: UriConverter = UriConverter()
) {

    fun convert(source: MediaEntity) =
        if (!source.isAnimated) {
            if (source.isImageType()) {
                convertImage(source)
            } else {
                convertUnknown(source)
            }
        } else {
            if (source.isAnimationType()) {
                convertAnimation(source)
            } else if (source.isVideoType()) {
                convertVideo(source)
            } else {
                convertUnknown(source)
            }
        }

    fun convert(source: GalleryMediaEntity) =
        if (!source.isAnimated) {
            if (source.isImageType()) {
                convertImage(source)
            } else {
                convertUnknown(source)
            }
        } else {
            if (source.isAnimationType()) {
                convertAnimation(source)
            } else if (source.isVideoType()) {
                convertVideo(source)
            } else {
                convertUnknown(source)
            }
        }

    private fun MediaEntity.isImageType() = type == JPEG || type == PNG || type == GIF

    private fun MediaEntity.isAnimationType() = type == GIF

    private fun MediaEntity.isVideoType() = type == MP4

    private fun GalleryMediaEntity.isImageType() = type == JPEG || type == PNG || type == GIF

    private fun GalleryMediaEntity.isAnimationType() = type == GIF

    private fun GalleryMediaEntity.isVideoType() = type == MP4


    private fun convertImage(source: MediaEntity): Media {
        val link = uriConverter.convert(source.link) ?: return convertUnknown(source)
        return Media.Image(
            id = source.id,
            title = source.title.orEmpty(),
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section,
            link = link,
        )
    }

    private fun convertAnimation(source: MediaEntity): Media {
        val link = uriConverter.convert(source.link) ?: return convertUnknown(source)
        return Media.Animation(
            id = source.id,
            title = source.title.orEmpty(),
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section,
            link = link,
            mp4 = uriConverter.convert(source.mp4)
        )
    }


    private fun convertVideo(source: MediaEntity): Media {
        val link = uriConverter.convert(source.link) ?: return convertUnknown(source)
        return Media.Video(
            id = source.id,
            title = source.title.orEmpty(),
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section,
            link = link,
            hasSound = source.hasSound
        )
    }


    private fun convertUnknown(source: MediaEntity) =
        Media.Unknown(
            id = source.id,
            title = source.title.orEmpty(),
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section
        )

    private fun convertImage(source: GalleryMediaEntity): Media {
        val link = uriConverter.convert(source.link) ?: return convertUnknown(source)
        return Media.Image(
            id = source.id,
            title = source.title,
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section,
            link = link,
        )
    }

    private fun convertAnimation(source: GalleryMediaEntity): Media {
        val link = uriConverter.convert(source.link) ?: return convertUnknown(source)
        return Media.Animation(
            id = source.id,
            title = source.title,
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section,
            link = link,
            mp4 = uriConverter.convert(source.mp4)
        )
    }


    private fun convertVideo(source: GalleryMediaEntity): Media {
        val link = uriConverter.convert(source.link) ?: return convertUnknown(source)
        return Media.Video(
            id = source.id,
            title = source.title,
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section,
            link = link,
            hasSound = source.hasSound
        )
    }


    private fun convertUnknown(source: GalleryMediaEntity) =
        Media.Unknown(
            id = source.id,
            title = source.title,
            description = source.description,
            type = source.type,
            width = source.width,
            height = source.height,
            name = source.name,
            section = source.section
        )

    fun convert(sourceList: List<MediaEntity>) = sourceList.map { convert(it) }

    companion object {
        const val JPEG = "image/jpeg"
        const val PNG = "image/png"
        const val GIF = "image/gif"
        const val MP4 = "video/mp4"
    }
}