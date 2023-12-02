package ru.leisure.imgur.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GalleryAlbumEntity(
    @JsonProperty("id") val id: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("link") val link: String,
    @JsonProperty("score") val score: Int,
    @JsonProperty("is_album") val isAlbum: Boolean,
    @JsonProperty("comment_count") val commentCount: Int,
    @JsonProperty("images_count") val imagesCount: Int,
    @JsonProperty("images") val images: List<ImageEntity>?,
)