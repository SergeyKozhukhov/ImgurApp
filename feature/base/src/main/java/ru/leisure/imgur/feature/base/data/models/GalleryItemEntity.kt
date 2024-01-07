package ru.leisure.imgur.feature.base.data.models

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
        value = GalleryMediaEntity::class,
        name = "false"
    )
)
sealed interface GalleryItemEntity {
    val id: String
}