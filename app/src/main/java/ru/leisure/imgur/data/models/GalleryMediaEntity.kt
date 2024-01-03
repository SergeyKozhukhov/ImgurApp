package ru.leisure.imgur.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GalleryMediaEntity(
    @JsonProperty("id") override val id: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String?,
    @JsonProperty("type") val type: String,
    @JsonProperty("animated") val isAnimated: Boolean,
    @JsonProperty("name") val name: String?,
    @JsonProperty("section") val section: String?,
    @JsonProperty("link") val link: String,
    @JsonProperty("gifv") val gifv: String?,
    @JsonProperty("mp4") val mp4: String?,
    @JsonProperty("has_sound") val hasSound: Boolean,
    @JsonProperty("comment_count") val commentCount: Int,
    @JsonProperty("score") val score: Int
) : GalleryItemEntity