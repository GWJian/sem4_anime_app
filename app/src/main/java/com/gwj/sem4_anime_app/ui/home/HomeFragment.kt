package com.gwj.sem4_anime_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.FragmentHomeBinding
import com.gwj.sem4_anime_app.ui.adapter.HorizontalTopAnimeAdapter
import com.gwj.sem4_anime_app.ui.adapter.SeasonNowAnimeAdapter
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var HorizontalTopAnimeAdapter: HorizontalTopAnimeAdapter
    private lateinit var SeasonNowAnimeAdapter: SeasonNowAnimeAdapter

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
                    TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(
                        animeId.mal_id.toString()
                    )
                navController.navigate(action)
            }
        }
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalTopAnimeRecycleView.adapter = HorizontalTopAnimeAdapter
        binding.horizontalTopAnimeRecycleView.layoutManager = layoutManager
    }


    private fun setupRecommendedAnimeAdapter() {
        //create button and check
        SeasonNowAnimeAdapter = SeasonNowAnimeAdapter(emptyList())
        SeasonNowAnimeAdapter.listener = object : SeasonNowAnimeAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(
                        animeId.mal_id.toString()
                    )
                navController.navigate(action)
            }
        }

        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.verticalAnimeRecyclerView.adapter = SeasonNowAnimeAdapter
        binding.verticalAnimeRecyclerView.layoutManager = layoutManager

        //Join two list
        binding.verticalAnimeRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val animeLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = animeLayoutManager.itemCount
                val lastAnimeItem = animeLayoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= lastAnimeItem + 2) {
                    viewModel.loadMoreItems()
                }
            }

        })

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        //===================== lifecycleScope topanimes Start =====================
        lifecycleScope.launch {
            viewModel.topAnimes.collect() {
                HorizontalTopAnimeAdapter.setTopAnimes(it)
            }
        }
        //===================== lifecycleScope topanimes End =====================

        //====================== lifecycleScope SeasonNow
        lifecycleScope.launch {
            viewModel.seasonNowAnimes.collect() {
                SeasonNowAnimeAdapter.setSeasonNowAnimes(it)
            }
        }

    }
}