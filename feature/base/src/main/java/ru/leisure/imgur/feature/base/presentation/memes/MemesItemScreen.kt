package ru.leisure.imgur.feature.base.presentation.memes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.leisure.imgur.feature.base.R
import ru.leisure.imgur.feature.base.domain.models.Media
import ru.leisure.imgur.feature.base.presentation.components.ErrorMessage
import ru.leisure.imgur.feature.base.presentation.components.ProgressBar

@Composable
fun MemesItemScreen(
    imageId: String,
    viewModel: MemesViewModel = viewModel(factory = MemesViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        MemesUiState.Loading -> LoadingUiState()
        is MemesUiState.Success -> SuccessUiState(
            imageId = imageId,
            memes = (uiState as MemesUiState.Success).memes,
        )

        is MemesUiState.Error -> ErrorUiState(message = (uiState as MemesUiState.Error).message)
    }
}

@Composable
private fun LoadingUiState() {
    ProgressBar(modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SuccessUiState(
    imageId: String,
    memes: List<Media>,
) {
    memes.indexOfFirst { it.id == imageId }.takeIf { it >= 0 }?.let { initIndex ->
        val pagerState = rememberPagerState(initialPage = initIndex, pageCount = { memes.size })
        HorizontalPager(state = pagerState) { index ->
            MemeItem(
                media = memes[index],
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun MemeItem(
    media: Media, modifier: Modifier = Modifier
) {
    when (media) {
        is Media.Image -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.link.toString())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = null,
                modifier = modifier
            )
        }

        is Media.Animation -> {
            Text("Animation")
        }

        is Media.Video -> {
            Text("Video")
        }

        is Media.Unknown -> {
            Text("Unknown")
        }
    }
}

@Composable
private fun ErrorUiState(message: String) {
    ErrorMessage(message = message, modifier = Modifier.fillMaxSize())
}

