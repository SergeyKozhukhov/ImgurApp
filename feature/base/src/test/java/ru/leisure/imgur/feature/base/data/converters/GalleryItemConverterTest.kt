package ru.leisure.imgur.feature.base.data.converters

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import ru.leisure.imgur.feature.base.data.models.GalleryAlbumEntity
import ru.leisure.imgur.feature.base.data.models.GalleryMediaEntity
import ru.leisure.imgur.feature.base.domain.models.GalleryAlbum
import ru.leisure.imgur.feature.base.domain.models.GalleryMedia

internal class GalleryItemConverterTest {

    private val albumConverter: GalleryAlbumConverter = mockk()
    private val mediaConverter: GalleryMediaConverter = mockk()

    private val converter = spyk(GalleryItemConverter(albumConverter, mediaConverter))

    @Test
    fun `convert album`() {
        val album: GalleryAlbum = mockk()
        val albumEntity: GalleryAlbumEntity = mockk()
        every { albumConverter.convert(albumEntity) } returns album
        val output = converter.convert(albumEntity)
        Truth.assertThat(output).isEqualTo(album)
        verify { albumConverter.convert(albumEntity) }
        verify(exactly = 0) { mediaConverter.convert(any<GalleryMediaEntity>()) }
    }

    @Test
    fun `convert media`() {
        val media: GalleryMedia = mockk()
        val mediaEntity: GalleryMediaEntity = mockk()
        every { mediaConverter.convert(mediaEntity) } returns media
        val output = converter.convert(mediaEntity)
        Truth.assertThat(output).isEqualTo(media)
        verify { mediaConverter.convert(mediaEntity) }
        verify(exactly = 0) { albumConverter.convert(any<GalleryAlbumEntity>()) }
    }

    @Test
    fun convertList() {
        val album: GalleryAlbum = mockk()
        val albumEntity: GalleryAlbumEntity = mockk()
        val media: GalleryMedia = mockk()
        val mediaEntity: GalleryMediaEntity = mockk()
        every { converter.convert(albumEntity) } returns album
        every { converter.convert(mediaEntity) } returns media
        val output = converter.convert(sourceList = listOf(albumEntity, mediaEntity))
        Truth.assertThat(output).isEqualTo(listOf(album, media))
        verify(exactly = 1) { converter.convert(albumEntity) }
        verify(exactly = 1) { converter.convert(mediaEntity) }
    }
}