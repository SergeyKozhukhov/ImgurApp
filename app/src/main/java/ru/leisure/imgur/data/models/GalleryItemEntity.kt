package ru.leisure.imgur.data.models

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "is_album",
)
@JsonSubTypes(
    JsonSubTypes.Type(
        value = GalleryAlbumEntity::class,
        name = "true"
    ),
    JsonSubTypes.Type(
        value = GalleryImageEntity::class,
        name = "false"
    )
)
sealed interface GalleryItemEntity {
    val id: String
}