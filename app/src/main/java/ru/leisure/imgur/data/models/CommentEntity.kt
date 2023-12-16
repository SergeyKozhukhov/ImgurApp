package ru.leisure.imgur.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CommentEntity(
    @JsonProperty("id") val id: Long,
    @JsonProperty("image_id") val imageId: String,
    @JsonProperty("comment") val comment: String,
    @JsonProperty("author") val author: String,
    @JsonProperty("on_album") val onAlbum: Boolean,
    @JsonProperty("album_cover") val albumCover: String,
    @JsonProperty("datetime") val datetime: Long,
    @JsonProperty("parent_id") val parentId: Long,
    @JsonProperty("children") val children: List<CommentEntity>,
)