package ru.leisure.imgur.feature.base.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class BasicEntity<T>(
    @JsonProperty("data") val data: T,
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("status") val status: Int
)