package ru.leisure.imgur.feature.base.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GalleryAlbumEntity(
    @JsonProperty("id") override val id: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("link") val link: String,
    @JsonProperty("score") val score: Int,
    @JsonProperty("comment_count") val commentCount: Int,
    @JsonProperty("images_count") val mediaCount: Int,
    @JsonProperty("images") val mediaList: List<MediaEntity>,
) : GalleryItemEntity