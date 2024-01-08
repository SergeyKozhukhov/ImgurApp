package ru.leisure.imgur.feature.base.presentation.tags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.leisure.imgur.feature.base.domain.models.GalleryTags
import ru.leisure.imgur.feature.base.domain.models.MediaTag
import ru.leisure.imgur.feature.base.domain.models.Tag
import ru.leisure.imgur.feature.base.domain.models.Topic
import ru.leisure.imgur.feature.base.presentation.components.ErrorMessage
import ru.leisure.imgur.feature.base.presentation.components.GalleryItemThumbnail
import ru.leisure.imgur.feature.base.presentation.components.ProgressBar


@Composable
fun TagsScreen(
    viewModel: DefaultGalleryTagsViewModel = viewModel(factory = DefaultGalleryTagsViewModel.Factory),
    onTopicClick: (String) -> Unit,
) {
    LaunchedEffect(Unit) { viewModel.loadGalleryTags() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isDefaultTagsLoading) LoadingUiState()

    if (uiState.defaultTags != null) SuccessUiState(
        galleryTags = uiState.defaultTags!!,
        isMediaTagLoading = uiState.isMediaTagLoading,
        mediaTag = uiState.mediaTag,
        mediaTagError = uiState.mediaTagError,
        onTagClick = { tag -> viewModel.onTagClick(tag) },
        onTopicClick = onTopicClick
    )

    uiState.defaultTagsError?.let { ErrorUiState(message = it) }
}

@Composable
private fun LoadingUiState() {
    ProgressBar(modifier = Modifier.fillMaxSize())
}

@Composable
private fun SuccessUiState(
    galleryTags: GalleryTags,
    isMediaTagLoading: Boolean,
    mediaTag: MediaTag?,
    mediaTagError: String?,
    onTagClick: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    TagsContainer(
        galleryTags = galleryTags,
        mediaTag = mediaTag,
        onTagClick = onTagClick,
        onTopicClick = onTopicClick,
        content = {
            if (isMediaTagLoading) {
                LoadingUiState()
            } else if (mediaTag != null) {
                MediaTagTitle(mediaTag.name)
                MediaTagItems(mediaTag)
            } else if (mediaTagError != null) {
                ErrorUiState(message = mediaTagError)
            }
        }
    )
}

@Composable
fun TagsContainer(
    galleryTags: GalleryTags,
    mediaTag: MediaTag?,
    onTopicClick: (String) -> Unit,
    onTagClick: (String) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val tagModifier = Modifier.padding(4.dp)
    Column {
        LazyRow { items(galleryTags.topics) { topic -> TopicItem(topic, onTopicClick) } }
        Spacer(modifier = Modifier.height(12.dp))
        TagsTitle()
        LazyRow {
            items(galleryTags.tags) { tag ->
                TagItem(
                    tag = tag,
                    isSelected = mediaTag?.name == tag.name,
                    onTagClick = onTagClick,
                    modifier = tagModifier
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        content.invoke(this)
    }
}

@Composable
fun TagsTitle() {
    Text(
        text = "Tags",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TagItem(
    tag: Tag,
    isSelected: Boolean,
    onTagClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = if (isSelected) CardDefaults.elevatedCardColors() else CardDefaults.cardColors(),
        onClick = { onTagClick.invoke(tag.name) },
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = tag.displayName)
            Text(text = "Posts: ${tag.totalItems}")
        }
    }
}

@Composable
private fun TopicItem(
    topic: Topic, onItemClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = topic.name,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = topic.description,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
        GalleryItemThumbnail(
            topic.topPost,
            onItemClick = onItemClick,
            modifier = Modifier
                .padding(4.dp)
                .width(300.dp)
        )
    }
}

@Composable
fun MediaTagTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun MediaTagItems(mediaTag: MediaTag) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(mediaTag.items) {
            StrictGalleryItemThumbnail(
                it,
                onItemClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}