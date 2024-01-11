package com.gwj.sem4_anime_app.ui.home

import android.os.Bundle
import android.util.Log
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
import com.gwj.sem4_anime_app.ui.adapter.BaseSeasonNowAnimeAdapter
import com.gwj.sem4_anime_app.ui.adapter.HorizontalTopAnimeAdapter
import com.gwj.sem4_anime_app.ui.adapter.SeasonNowAnimeAdapter
import com.gwj.sem4_anime_app.ui.adapter.SeasonNowAnimeLinearAdapter
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var HorizontalTopAnimeAdapter: HorizontalTopAnimeAdapter
    private lateinit var SeasonNowAnimeAdapter: BaseSeasonNowAnimeAdapter

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
        setupRecommendedAnimeAdapter(false)

        binding.toggleBtnGrid.setOnClickListener {
            viewModel.setGridView()
        }

        binding.toggleBtnLinear.setOnClickListener {
            viewModel.setLinearView()
        }
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


    private fun setupRecommendedAnimeAdapter(grid: Boolean, animes: List<Data> = emptyList()) {
        if (grid) {
            SeasonNowAnimeAdapter = SeasonNowAnimeAdapter(animes)

            val layoutManager = GridLayoutManager(requireContext(), 3)
            binding.verticalAnimeRecyclerView.adapter = SeasonNowAnimeAdapter
            binding.verticalAnimeRecyclerView.layoutManager = layoutManager
        } else {
            SeasonNowAnimeAdapter = SeasonNowAnimeLinearAdapter(animes)

            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.verticalAnimeRecyclerView.adapter = SeasonNowAnimeAdapter
            binding.verticalAnimeRecyclerView.layoutManager = layoutManager
        }

        SeasonNowAnimeAdapter.listener = object : BaseSeasonNowAnimeAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(
                        animeId.mal_id.toString()
                    )
                navController.navigate(action)
            }
        }

        //Join two list
        binding.verticalAnimeRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //try grid,if no throw Linear
                val animeLayoutManager =
                    try {
                        recyclerView.layoutManager as GridLayoutManager
                    } catch (e: Exception) {
                        recyclerView.layoutManager as LinearLayoutManager
                    }

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

        //====================== lifecycleScope SeasonNow Start ==================
        lifecycleScope.launch {
            viewModel.seasonNowAnimes.collect {
                SeasonNowAnimeAdapter.baseSetSeasonNowAnimes(it)
            }
        }

        lifecycleScope.launch {
            viewModel.toggleIsGridOrLinear.collect {
                setupRecommendedAnimeAdapter(it.first, it.second)
            }
        }

        lifecycleScope.launch {
            // Observe the isLoading LiveData from the ViewModel and show/hide the progress bar when it changes
            viewModel.isLoadingMoreItems.collect {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        //====================== lifecycleScope SeasonNow End ==================

    }
}