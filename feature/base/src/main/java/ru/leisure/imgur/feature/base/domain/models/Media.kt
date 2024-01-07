package ru.leisure.imgur.feature.base.domain.models

import java.net.URI

sealed class Media {

    abstract val id: String
    abstract val title: String
    abstract val description: String?
    abstract val type: String
    abstract val width: Int
    abstract val height: Int
    abstract val views: Int
    abstract val name: String?
    abstract val section: String?

    data class Image(
        override val id: String,
        override val title: String,
        override val description: String?,
        override val type: String,
        override val width: Int,
        override val height: Int,
        override val views: Int,
        override val name: String?,
        override val section: String?,
        val link: URI
    ) : Media()

    data class Animation(
        override val id: String,
        override val title: String,
        override val description: String?,
        override val type: String,
        override val width: Int,
        override val height: Int,
        override val views: Int,
        override val name: String?,
        override val section: String?,
        val link: URI,
        val mp4: URI?,
    ) : Media()

    data class Video(
        override val id: String,
        override val title: String,
        override val description: String?,
        override val type: String,
        override val width: Int,
        override val height: Int,
        override val views: Int,
        override val name: String?,
        override val section: String?,
        val link: URI,
        val hasSound: Boolean
    ) : Media()

    data class Unknown(
        override val id: String,
        override val title: String,
        override val description: String?,
        override val type: String,
        override val width: Int,
        override val height: Int,
        override val views: Int,
        override val name: String?,
        override val section: String?
    ) : Media()
}