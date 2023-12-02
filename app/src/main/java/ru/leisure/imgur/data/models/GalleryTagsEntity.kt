package ru.leisure.imgur.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GalleryTagsEntity(
    @JsonProperty("tags") val tags: List<TagEntity>,
    @JsonProperty("featured") val featured: String?,
    @JsonProperty("galleries") val topics: List<TopicEntity>,
)