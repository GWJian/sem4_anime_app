package com.gwj.sem4_anime_app.ui.vidview

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.databinding.FragmentVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    override val viewModel: VideoViewModel by viewModels()
    val args: VideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        viewModel.getAnimeVideo(args.animeId.toInt())
    }


    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.animeVideo.collect { animeVideo ->
//                setupYouTubePlayer(binding.youtubePlayerView, animeVideo.trailer.url) //can't use lol
                animeVideo?.let {
                    setupYouTubePlayer(binding.youtubePlayerView, it.trailer.url)
                }
            }
        }
    }

    //TODO some video will crash the app,find out why
    private fun setupYouTubePlayer(youTubePlayerView: YouTubePlayerView, url: String) {
        val videoId = Uri.parse(url).getQueryParameter("v")

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        })
    }


}