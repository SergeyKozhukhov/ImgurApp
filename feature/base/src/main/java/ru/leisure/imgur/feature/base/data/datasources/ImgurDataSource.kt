package ru.leisure.imgur.feature.base.data.datasources

import androidx.annotation.WorkerThread
import ru.leisure.imgur.core.network.api.NetworkException
import ru.leisure.imgur.core.parser.api.ParserException
import ru.leisure.imgur.feature.base.data.models.BasicEntity
import ru.leisure.imgur.feature.base.data.models.CommentEntity
import ru.leisure.imgur.feature.base.data.models.GalleryAlbumEntity
import ru.leisure.imgur.feature.base.data.models.GalleryItemEntity
import ru.leisure.imgur.feature.base.data.models.GalleryMediaEntity
import ru.leisure.imgur.feature.base.data.models.GalleryTagsEntity
import ru.leisure.imgur.feature.base.data.models.MediaEntity

interface ImgurDataSource {

    @WorkerThread
    @Throws(NetworkException::class, ParserException::class)
    fun getDefaultMemes(): BasicEntity<List<MediaEntity>>

    @WorkerThread
    @Throws(NetworkException::class, ParserException::class)
    fun getGallery(page: Int): BasicEntity<List<GalleryItemEntity>>

    @WorkerThread
    @Throws(NetworkException::class, ParserException::class)
    fun getGalleryAlbum(id: String): BasicEntity<GalleryAlbumEntity>

    @WorkerThread
    @Throws(NetworkException::class, ParserException::class)
    fun getGalleryMedia(id: String): BasicEntity<GalleryMediaEntity>

    @WorkerThread
    @Throws(NetworkException::class, ParserException::class)
    fun getDefaultGalleryTags(): BasicEntity<GalleryTagsEntity>

    @WorkerThread
    @Throws(NetworkException::class, ParserException::class)
    fun searchGallery(query: String): BasicEntity<List<GalleryItemEntity>>

    @WorkerThread
    @Throws(NetworkException::class, ParserException::class)
    fun getComments(id: String): BasicEntity<List<CommentEntity>>
}