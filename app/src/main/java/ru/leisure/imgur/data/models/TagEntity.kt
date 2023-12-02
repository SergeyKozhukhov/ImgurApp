package ru.leisure.imgur.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TagEntity(
    @JsonProperty("name") val name: String,
    @JsonProperty("display_name") val displayName: String,
    @JsonProperty("followers") val followers: String,
    @JsonProperty("total_items") val totalItems: String,
    @JsonProperty("description") val description: String
)