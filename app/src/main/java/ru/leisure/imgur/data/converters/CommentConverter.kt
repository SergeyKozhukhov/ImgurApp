package ru.leisure.imgur.data.converters

import ru.leisure.imgur.data.models.CommentEntity
import ru.leisure.imgur.domain.models.Comment

class CommentConverter {

    fun convert(source: CommentEntity) =
        Comment(
            id = source.id,
            imageId = source.imageId,
            comment = source.comment,
            author = source.author,
            onAlbum = source.onAlbum,
            albumCover = source.albumCover,
            datetime = source.datetime,
            parentId = source.parentId,
            children = emptyList(),
        )

    fun convert(sourceList: List<CommentEntity>) = sourceList.map { convert(it) }
}