package com.gwj.sem4_anime_app.ui.vidview

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.gwj.sem4_anime_app.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.utils.FadeViewHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBarListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class CustomPlayerUiController(
    customUI: View,
    private val youTubePlayer: YouTubePlayer,
    private val playerTracker: YouTubePlayerTracker = YouTubePlayerTracker()
) : AbstractYouTubePlayerListener() {

    init {
        // Add the tracker as a listener to the YouTube player.
        youTubePlayer.addListener(playerTracker)
        initViews(customUI)
    }

    private fun initViews(view: View) {
        val container: View = view.findViewById(R.id.container)
        val relativeLayout: RelativeLayout = view.findViewById(R.id.root)
        val seekBar: YouTubePlayerSeekBar = view.findViewById(R.id.playerSeekbar)
        val pausePlay: ImageButton = view.findViewById(R.id.pausePlay)
        // Add the seekBar as a listener to the YouTube player.
        youTubePlayer.addListener(seekBar)

        // Set a listener for the seekBar.
        seekBar.youtubePlayerSeekBarListener = object : YouTubePlayerSeekBarListener {
            override fun seekTo(time: Float) {
                // Seek to the specified time in the video.
                youTubePlayer.seekTo(time)
            }
        }

        // Set a click listener for the pause/play button.
        pausePlay.setOnClickListener {
            if (playerTracker.state == PlayerConstants.PlayerState.PLAYING) {
                pausePlay.setImageResource(R.drawable.ic_play_circle_filled)
                youTubePlayer.pause()
            } else {
                pausePlay.setImageResource(R.drawable.ic_pause_circle_filled)
                youTubePlayer.play()
            }
        }

        // Helper for fading the view.
        val fadeViewHelper = FadeViewHelper(container)
        fadeViewHelper.animationDuration
        fadeViewHelper.fadeOutDelay
        // Add the fadeViewHelper as a listener to the YouTube player.
        youTubePlayer.addListener(fadeViewHelper)

        // Set click listeners to toggle the visibility of the controls.
        relativeLayout.setOnClickListener { fadeViewHelper.toggleVisibility() }
        container.setOnClickListener { fadeViewHelper.toggleVisibility() }
    }
}