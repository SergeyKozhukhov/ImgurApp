package ru.leisure.imgur.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GalleryImageEntity(
    @JsonProperty("id") override val id: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("type") val type: String,
    @JsonProperty("link") val link: String,
    @JsonProperty("gifv") val gifv: String?,
    @JsonProperty("mp4") val mp4: String?,
    @JsonProperty("comment_count") val commentCount: Int,
    @JsonProperty("score") val score: Int
) : GalleryItemEntity