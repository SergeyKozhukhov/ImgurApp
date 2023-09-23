package ru.leisure.imgur.presentation.memes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.R
import ru.leisure.imgur.domain.Image

@Composable
fun MemesScreen(viewModel: MemesViewModel) {
    LaunchedEffect(true) {
        viewModel.loadMemes()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        MemesUiState.Loading -> LoadingUiState()
        is MemesUiState.Success -> SuccessUiState(memes = (uiState as MemesUiState.Success).memes)
        is MemesUiState.Error -> ErrorUiState(message = (uiState as MemesUiState.Error).message)
    }
}


@Composable
private fun LoadingUiState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessUiState(memes: List<Image>) {
    LazyColumn {
        items(memes) { image -> MemeItem(image) }
    }
}

@Composable
fun MemeItem(image: Image) {
    Row(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.link)
                .placeholder(R.drawable.ic_launcher_foreground)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
        )
        Column {
            Text(text = image.title)
            Text(text = image.description.orEmpty())
        }
    }
}

@Composable
private fun ErrorUiState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}

