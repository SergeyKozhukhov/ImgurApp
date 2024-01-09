package ru.leisure.imgur.feature.base.domain

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.leisure.imgur.feature.base.domain.models.Comment
import ru.leisure.imgur.feature.base.domain.models.GalleryAlbum
import ru.leisure.imgur.feature.base.domain.models.GalleryItem
import ru.leisure.imgur.feature.base.domain.models.GalleryMedia
import ru.leisure.imgur.feature.base.domain.models.GalleryTags
import ru.leisure.imgur.feature.base.domain.models.Media
import ru.leisure.imgur.feature.base.domain.models.MediaTag


internal class ImgurInteractorImplTest {

    private val repository: ImgurRepository = mockk()

    private val interactor = ImgurInteractorImpl(repository)

    @Test
    fun getDefaultMemes() = runTest {
        val media: Media = mockk()
        coEvery { repository.getDefaultMemes() } returns listOf(media)
        val output = interactor.getDefaultMemes()
        assertThat(output).isEqualTo(listOf(media))
        coVerify { repository.getDefaultMemes() }
    }

    @Test
    fun getGallery() = runTest {
        val item: GalleryItem = mockk()
        coEvery { repository.getGallery(2) } returns listOf(item)
        val output = interactor.getGallery(2)
        assertThat(output).isEqualTo(listOf(item))
        coVerify { repository.getGallery(2) }
    }

    @Test
    fun getGalleryAlbum() = runTest {
        val album: GalleryAlbum = mockk()
        coEvery { repository.getGalleryAlbum("id") } returns album
        val output = interactor.getGalleryAlbum("id")
        assertThat(output).isEqualTo(album)
        coVerify { repository.getGalleryAlbum("id") }
    }

    @Test
    fun getGalleryMedia() = runTest {
        val media: GalleryMedia = mockk()
        coEvery { repository.getGalleryMedia("id") } returns media
        val output = interactor.getGalleryMedia("id")
        assertThat(output).isEqualTo(media)
        coVerify { repository.getGalleryMedia("id") }
    }

    @Test
    fun getDefaultGalleryTags() = runTest {
        val galleryTags: GalleryTags = mockk()
        coEvery { repository.getDefaultGalleryTags() } returns galleryTags
        val output = interactor.getDefaultGalleryTags()
        assertThat(output).isEqualTo(galleryTags)
        coVerify { repository.getDefaultGalleryTags() }
    }

    @Test
    fun getMediaTag() = runTest {
        val mediaTag: MediaTag = mockk()
        coEvery { repository.getMediaTag("tag") } returns mediaTag
        val output = interactor.getMediaTag("tag")
        assertThat(output).isEqualTo(mediaTag)
        coVerify { repository.getMediaTag("tag") }
    }

    @Test
    fun searchGallery() = runTest {
        val item: GalleryItem = mockk()
        coEvery { repository.searchGallery("query") } returns listOf(item)
        val output = interactor.searchGallery("query")
        assertThat(output).isEqualTo(listOf(item))
        coVerify { repository.searchGallery("query") }
    }

    @Test
    fun getComments() = runTest {
        val comment: Comment = mockk()
        coEvery { repository.getComments("id") } returns listOf(comment)
        val output = interactor.getComments("id")
        assertThat(output).isEqualTo(listOf(comment))
        coVerify { repository.getComments("id") }
    }

}