package ru.leisure.imgur.feature.base.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MediaEntity(
    @JsonProperty("id") val id: String,
    @JsonProperty("title") val title: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("type") val type: String,
    @JsonProperty("animated") val isAnimated: Boolean,
    @JsonProperty("width") val width: Int,
    @JsonProperty("height") val height: Int,
    @JsonProperty("views") val views: Int,
    @JsonProperty("name") val name: String?,
    @JsonProperty("section") val section: String?,
    @JsonProperty("link") val link: String,
    @JsonProperty("gifv") val gifv: String?,
    @JsonProperty("mp4") val mp4: String?,
    @JsonProperty("has_sound") val hasSound: Boolean
)