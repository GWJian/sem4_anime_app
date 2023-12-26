package com.gwj.sem4_anime_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.FragmentHomeBinding
import com.gwj.sem4_anime_app.ui.adapter.HorizontalTopAnimeAdapter
import com.gwj.sem4_anime_app.ui.adapter.VerticalAnimeAdapter
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragment
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var HorizontalTopAnimeAdapter: HorizontalTopAnimeAdapter
    private lateinit var VerticalAnimeAdapter: VerticalAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        setupTopAnimeAdapter()
        setupRecommendedAnimeAdapter()
    }

    private fun setupTopAnimeAdapter() {
        HorizontalTopAnimeAdapter = HorizontalTopAnimeAdapter(emptyList())
        HorizontalTopAnimeAdapter.listener = object : HorizontalTopAnimeAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(animeId.mal_id.toString())
                navController.navigate(action)
            }
        }
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalTopAnimeRecycleView.adapter = HorizontalTopAnimeAdapter
        binding.horizontalTopAnimeRecycleView.layoutManager = layoutManager
    }


    private fun setupRecommendedAnimeAdapter() {
        VerticalAnimeAdapter = VerticalAnimeAdapter(emptyList())
        VerticalAnimeAdapter.listener = object : VerticalAnimeAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action = TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(animeId.mal_id.toString())
                navController.navigate(action)
            }
        }

        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.verticalAnimeRecyclerView.adapter = VerticalAnimeAdapter
        binding.verticalAnimeRecyclerView.layoutManager = layoutManager

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        //===================== lifecycleScope animes Start =====================
        lifecycleScope.launch {
            viewModel.animes.collect() {
                HorizontalTopAnimeAdapter.setTopAnimes(it)
            }
        }
        //===================== lifecycleScope animes End =====================

        lifecycleScope.launch {
            viewModel.animes.collect() {
                VerticalAnimeAdapter.setTopAnimes(it)
            }
        }

    }
}