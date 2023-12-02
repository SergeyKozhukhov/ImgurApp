package ru.leisure.imgur.data.datasources

import androidx.annotation.WorkerThread
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import ru.leisure.imgur.data.models.BasicEntity
import ru.leisure.imgur.data.models.GalleryAlbumEntity
import ru.leisure.imgur.data.models.GalleryTagsEntity
import ru.leisure.imgur.data.models.ImageEntity
import ru.leisure.imgur.data.models.ImgurResponseException
import java.io.IOException

interface ImgurDataSource {

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun getDefaultMemes(): BasicEntity<List<ImageEntity>>

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun getGallery(): BasicEntity<List<GalleryAlbumEntity>>

    @WorkerThread
    @Throws(
        IOException::class,
        JsonProcessingException::class,
        JsonMappingException::class,
        ImgurResponseException::class
    )
    fun getDefaultGalleryTags(): BasicEntity<GalleryTagsEntity>
}