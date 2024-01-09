package ru.leisure.imgur.common.video

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import java.net.URI

class VideoPlayerAdjuster : PlayerView {

    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    private var exoPlayer: ExoPlayer? = null

    private val playbackStateListener: Player.Listener = playbackStateListener()

    constructor(context: Context) : super(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    @androidx.media3.common.util.UnstableApi
    fun initializePlayer(url: URI) {
        setShowPreviousButton(false)
        setShowNextButton(false)
        controllerAutoShow = false
        setShowBuffering(SHOW_BUFFERING_ALWAYS)
        exoPlayer = ExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                player = exoPlayer
                val mediaItem = MediaItem.fromUri(url.toString())
                exoPlayer.setMediaItems(listOf(mediaItem), mediaItemIndex, playbackPosition)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.addListener(playbackStateListener)
                exoPlayer.prepare()
            }
    }

    fun useController(shouldUse: Boolean) {
        useController = shouldUse
    }

    fun useLooping(shouldLoop: Boolean) {
        exoPlayer?.repeatMode = if (shouldLoop) REPEAT_MODE_ONE else REPEAT_MODE_OFF
    }

    fun setSoundEnabled(isSoundEnabled: Boolean) {
        player?.volume = if (isSoundEnabled) 1f else 0f
    }

    fun releasePlayer() {
        exoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            mediaItemIndex = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.removeListener(playbackStateListener)
            exoPlayer.release()
        }
        exoPlayer = null
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Log.d(TAG, "changed state to $stateString")
        }
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow: ")
        releasePlayer()
        super.onDetachedFromWindow()
    }

    private companion object {
        private const val TAG = "VideoPlayerAdjuster"
    }
}