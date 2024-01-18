package com.gwj.sem4_anime_app.ui.vidview

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gwj.sem4_anime_app.ui.base.BaseFragment
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.databinding.FragmentVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
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
                animeVideo?.let {
                    setupYouTubePlayer(binding.youtubePlayerView, it.trailer.youtube_id)
                }
            }
        }
    }
//    WebView ver
//    private fun setupYouTubePlayer(youTubePlayerView: YouTubePlayerView, videoId: String) {
//        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                if (videoId.isNotEmpty()) {
//                    youTubePlayer.loadVideo(videoId, 0f)
//                }
//            }
//        })
//    }

    //Custom UI ver
    private fun setupYouTubePlayer(youTubePlayerView: YouTubePlayerView, videoId: String) {
        youTubePlayerView.enableAutomaticInitialization = false
        lifecycle.addObserver(youTubePlayerView)

        //val customPlayerUi: View = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_controls)
        val customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_controls)

        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val customPlayerUiController =
                    CustomPlayerUiController(customPlayerUi, youTubePlayer)
                youTubePlayer.addListener(customPlayerUiController)

                if (videoId.isNotEmpty()) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        }

        //to set different options into it
        val options = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)
    }


}