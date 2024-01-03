package ru.leisure.imgur.presentation.tags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import ru.leisure.imgur.domain.models.GalleryTags
import ru.leisure.imgur.domain.models.Tag
import ru.leisure.imgur.domain.models.Topic
import ru.leisure.imgur.presentation.components.ErrorMessage
import ru.leisure.imgur.presentation.components.GalleryItemThumbnail
import ru.leisure.imgur.presentation.components.ProgressBar


@Composable
fun TagsScreen(
    viewModel: DefaultGalleryTagsViewModel = viewModel(factory = DefaultGalleryTagsViewModel.Factory),
    onItemClick: (String) -> Unit,
) {
    LaunchedEffect(Unit) { viewModel.loadGalleryTags() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        DefaultGalleryTagsUiState.Idle, DefaultGalleryTagsUiState.Loading -> LoadingUiState()
        is DefaultGalleryTagsUiState.Success -> SuccessUiState(
            galleryTags = (uiState as DefaultGalleryTagsUiState.Success).tags,
            onItemClick = onItemClick
        )

        is DefaultGalleryTagsUiState.Error -> ErrorUiState(message = (uiState as DefaultGalleryTagsUiState.Error).message)
    }
}

@Composable
private fun LoadingUiState() {
    ProgressBar(modifier = Modifier.fillMaxSize())
}

@Composable
private fun SuccessUiState(
    galleryTags: GalleryTags,
    onItemClick: (String) -> Unit
) {
    val tagModifier = Modifier.padding(4.dp)
    Column {
        TagsTitle()
        LazyRow { items(galleryTags.tags) { tag -> TagItem(modifier = tagModifier, tag = tag) } }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow { items(galleryTags.topics) { topic -> TopicItem(topic, onItemClick) } }
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

@Composable
private fun TagItem(tag: Tag, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = tag.displayName)
            Text(text = "Posts: ${tag.totalItems}")
        }
    }
}

@Composable
private fun TopicItem(
    topic: Topic,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
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
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}