package ru.leisure.imgur.feature.base.presentation.components.video

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.util.UnstableApi
import java.net.URI

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    url: URI,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    var isPlayerAvailable by remember { mutableStateOf(false) }

    AndroidView(
        factory = { context ->
            VideoPlayerAdjuster(context)
        },
        update = { player ->
            if (isPlayerAvailable) {
                player.initializePlayer(url)
            } else {
                player.releasePlayer()
            }
        },
        modifier = modifier
    )

    DisposableEffect(lifecycleOwner) {
        val observer = createObserver(
            onStart = { isPlayerAvailable = true },
            onStop = { isPlayerAvailable = false }
        )
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}

private fun createObserver(onStart: () -> Unit, onStop: () -> Unit) =
    object : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) = onStart.invoke()

        override fun onStop(owner: LifecycleOwner) = onStop.invoke()
    }