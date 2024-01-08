package ru.leisure.imgur.feature.base.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MediaTagEntity(
    @JsonProperty("name") val name: String,
    @JsonProperty("followers") val followers: String,
    @JsonProperty("total_items") val totalItems: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("items") val items: List<GalleryItemEntity>
)