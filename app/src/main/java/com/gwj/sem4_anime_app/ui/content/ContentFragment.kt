package com.gwj.sem4_anime_app.ui.content

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.databinding.FragmentContentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentFragment : BaseFragment<FragmentContentBinding>() {
    override val viewModel: ContentViewModel by viewModels()
    val args: ContentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        viewModel.getAnimeDetail(args.animeId.toInt()) //get anime by id

        binding.contentBackBtn.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.anime.collect { animeDetail ->
                binding.run {
                    contentTitle.text = animeDetail?.title ?: "N/A"
                    contentTitleJP.text = animeDetail?.title_japanese ?: "N/A"
                    contentEpisodes.text = animeDetail?.episodes.toString()
                    contentYear.text = animeDetail?.aired?.string ?: "N/A"
                    contentStatus.text = animeDetail?.status ?: "N/A"
                    contentSource.text = animeDetail?.source ?: "N/A"
                    contentDuration.text = animeDetail?.duration ?: "N/A"
                    contentDesc.text = animeDetail?.synopsis ?: "No Description"

                    if (animeDetail?.trailer?.youtube_id.isNullOrEmpty()){
                        contentTrailer.visibility = View.GONE
                        contentNoTrailer.visibility = View.VISIBLE
                    } else {
                        contentTrailer.visibility = View.VISIBLE
                        contentNoTrailer.visibility = View.GONE
                        contentTrailer.setOnClickListener {
                            val action =
                                ContentFragmentDirections.actionContentFragmentToVideoFragment(
                                    animeDetail?.mal_id.toString()
                                )
                            navController.navigate(action)
                        }
                    }

                    Glide.with(binding.root)
                        .load(animeDetail?.images?.jpg?.image_url)
                        .into(contentImage)
                }
            }
        }

    }


}