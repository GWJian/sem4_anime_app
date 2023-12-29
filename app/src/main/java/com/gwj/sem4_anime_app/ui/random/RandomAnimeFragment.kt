package com.gwj.sem4_anime_app.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.databinding.FragmentRandomAnimeBinding
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RandomAnimeFragment : BaseFragment<FragmentRandomAnimeBinding>() {

    override val viewModel: RandomAnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.run {
            btnClickMe.setOnClickListener {
                viewModel.getRandomAnime()
            }

            tvRandomAnimeName.setOnClickListener {
                toContentFragment(viewModel.randomAnime.value?.mal_id)
            }

            ivRandomAnimeImg.setOnClickListener {
                toContentFragment(viewModel.randomAnime.value?.mal_id)
            }
        }

    }

    private fun toContentFragment(animeId: Int?) {
        //pass the mal_id to contentFragment argument animeId
        animeId?.let {
            val action =
                TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(it.toString())
            findNavController().navigate(action)
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.randomAnime.collect { randomAnime ->
                binding.run {
                    tvRandomAnimeName.text = randomAnime?.title
                    tvRandomType.text = randomAnime?.type

                    Glide.with(binding.root)
                        .load(randomAnime?.images?.jpg?.image_url)
                        .into(ivRandomAnimeImg)
                }
            }
        }

    }

}