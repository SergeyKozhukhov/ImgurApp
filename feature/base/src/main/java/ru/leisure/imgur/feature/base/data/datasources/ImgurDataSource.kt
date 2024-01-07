package ru.leisure.imgur.feature.base.data.datasources

import androidx.annotation.WorkerThread
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import ru.leisure.imgur.feature.base.data.models.BasicEntity
import ru.leisure.imgur.feature.base.data.models.CommentEntity
import ru.leisure.imgur.feature.base.data.models.GalleryItemEntity
import ru.leisure.imgur.feature.base.data.models.GalleryTagsEntity
import ru.leisure.imgur.feature.base.data.models.ImgurResponseException
import ru.leisure.imgur.feature.base.data.models.MediaEntity
import java.io.IOException

interface ImgurDataSource {

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun getDefaultMemes(): BasicEntity<List<MediaEntity>>

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun getGallery(page: Int): BasicEntity<List<GalleryItemEntity>>

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun getDefaultGalleryTags(): BasicEntity<GalleryTagsEntity>

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun searchGallery(query: String): BasicEntity<List<GalleryItemEntity>>

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun getComments(id: String): BasicEntity<List<CommentEntity>>
}