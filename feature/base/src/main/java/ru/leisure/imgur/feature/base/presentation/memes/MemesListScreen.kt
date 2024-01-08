package ru.leisure.imgur.feature.base.presentation.memes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.feature.base.R
import ru.leisure.imgur.feature.base.domain.models.Media
import ru.leisure.imgur.feature.base.presentation.components.ErrorUiState
import ru.leisure.imgur.feature.base.presentation.components.LoadingUiState

@Composable
fun MemesListScreen(
    viewModel: MemesViewModel = viewModel(factory = MemesViewModel.Factory),
    onItemClick: (String) -> Unit
) {
    LaunchedEffect(Unit) { viewModel.loadMemes() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        MemesUiState.Loading -> LoadingUiState()
        is MemesUiState.Success -> SuccessUiState(
            memes = (uiState as MemesUiState.Success).memes,
            onItemClick = onItemClick
        )

        is MemesUiState.Error -> ErrorUiState(message = (uiState as MemesUiState.Error).message)
    }
}

@Composable
private fun SuccessUiState(
    memes: List<Media>,
    onItemClick: (String) -> Unit
) {
    LazyColumn {
        items(memes) { image ->
            MemeItem(
                media = image,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun MemeItem(
    media: Media, modifier: Modifier = Modifier, onItemClick: (String) -> Unit
) {
    Card(
        modifier = modifier.clickable { onItemClick.invoke(media.id) },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when(media) {
                is Media.Image -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(media.link.toString())
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_background)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(86.dp)
                            .clip(CircleShape),
                    )
                }
                is Media.Video -> {
                    Text(text = "Video")
                }
                is Media.Animation -> {
                    Text(text = "Animation")
                }
                is Media.Unknown -> {
                    Text(text = "Unknown")
                }
            }
            Text(
                text = media.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
