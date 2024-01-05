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
    controlsUi: View,
    private val youTubePlayer: YouTubePlayer,
) : AbstractYouTubePlayerListener() {

    private val playerTracker: YouTubePlayerTracker = YouTubePlayerTracker()

    init {
        youTubePlayer.addListener(playerTracker)
        initViews(controlsUi)
    }

    private fun initViews(view: View) {
        val container: View = view.findViewById(R.id.container)
        val relativeLayout: RelativeLayout = view.findViewById(R.id.root)
        val seekBar: YouTubePlayerSeekBar = view.findViewById(R.id.playerSeekbar)
        val pausePlay: ImageButton = view.findViewById(R.id.pausePlay)
        youTubePlayer.addListener(seekBar)

        seekBar.youtubePlayerSeekBarListener = object : YouTubePlayerSeekBarListener {
            override fun seekTo(time: Float) {
                youTubePlayer.seekTo(time)
            }
        }

        pausePlay.setOnClickListener {
            if (playerTracker.state == PlayerConstants.PlayerState.PLAYING) {
                pausePlay.setImageResource(R.drawable.ic_play_circle_filled)
                youTubePlayer.pause()
            } else {
                pausePlay.setImageResource(R.drawable.ic_pause_circle_filled)
                youTubePlayer.play()
            }
        }

        val fadeViewHelper = FadeViewHelper(container)
        fadeViewHelper.animationDuration
        fadeViewHelper.fadeOutDelay
        youTubePlayer.addListener(fadeViewHelper)

        relativeLayout.setOnClickListener { fadeViewHelper.toggleVisibility() }
        container.setOnClickListener { fadeViewHelper.toggleVisibility() }
    }
}