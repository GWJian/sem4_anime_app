package com.gwj.sem4_anime_app.ui.vidview

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.databinding.FragmentVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    override val viewModel: VideoViewModel by viewModels()

    //TODO use youtube iframe,android-youtube-player? exo-player is hard

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        val fullUrl =
            "https://www.youtube.com/watch?v=3M1BTYwUYLs" // replace with the URL from the API
        val videoId = Uri.parse(fullUrl).getQueryParameter("v")

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        })
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
    }


}