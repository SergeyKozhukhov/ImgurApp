package ru.leisure.imgur.feature.base.data.converters

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import ru.leisure.imgur.feature.base.data.models.GalleryTagsEntity
import ru.leisure.imgur.feature.base.data.models.TagEntity
import ru.leisure.imgur.feature.base.data.models.TopicEntity
import ru.leisure.imgur.feature.base.domain.models.GalleryTags
import ru.leisure.imgur.feature.base.domain.models.Tag
import ru.leisure.imgur.feature.base.domain.models.Topic

internal class GalleryTagsConverterTest {

    private val tagConverter: TagConverter = mockk()
    private val topicConverter: TopicConverter = mockk()

    private val converter = GalleryTagsConverter(tagConverter, topicConverter)

    @Test
    fun convert() {
        val featured = "featured"
        val tags: List<Tag> = mockk()
        val topics: List<Topic> = mockk()
        val expected = GalleryTags(tags, featured, topics)

        val tagEntities: List<TagEntity> = mockk()
        val topicEntities: List<TopicEntity> = mockk()
        val source: GalleryTagsEntity = mockk()

        every { source.tags } returns tagEntities
        every { source.featured } returns featured
        every { source.topics } returns topicEntities
        every { tagConverter.convert(sourceList = tagEntities) } returns tags
        every { topicConverter.convert(sourceList = topicEntities) } returns topics

        val output = converter.convert(source)

        Truth.assertThat(output).isEqualTo(expected)
        verify { tagConverter.convert(tagEntities) }
        verify { topicConverter.convert(topicEntities) }
    }
}