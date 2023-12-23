package ru.leisure.imgur.domain.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Comment(
    val id: Long,
    val mediaId: String,
    val comment: String,
    val author: String,
    val onAlbum: Boolean,
    val albumCover: String?,
    val datetime: Long,
    val parentId: Long,
    val children: List<Comment>,
)