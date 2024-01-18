package com.gwj.sem4_anime_app.ui.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.model.FavouriteAnime
import com.gwj.sem4_anime_app.databinding.FragmentFavouriteBinding
import com.gwj.sem4_anime_app.ui.adapter.FavouriteAnimeAdapter
import com.gwj.sem4_anime_app.ui.adapter.HorizontalTopAnimeAdapter
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {

    override val viewModel: FavouriteViewModel by viewModels()
    private lateinit var FavouriteAnimeAdapter: FavouriteAnimeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        setupFavouriteAnimeAdapter()

        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("profile", 3)
            setFragmentResult("profile", bundle)
            val action = FavouriteFragmentDirections.faveToTabContainer()
            navController.navigate(action)
        }
    }

    private fun setupFavouriteAnimeAdapter() {
        FavouriteAnimeAdapter = FavouriteAnimeAdapter(emptyList())
        FavouriteAnimeAdapter.listener = object: FavouriteAnimeAdapter.Listener {
            override fun onClick(animeId: FavouriteAnime) {
                val action =
                    FavouriteFragmentDirections.favouriteFragmentToContentFragment(
                        animeId.mal_id.toString()
                    )
                navController.navigate(action)
            }
        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFavourite.adapter = FavouriteAnimeAdapter
        binding.rvFavourite.layoutManager = layoutManager
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.favourite.collect() {
                FavouriteAnimeAdapter.setFavouriteAnimes(it)
            }
        }
    }


}