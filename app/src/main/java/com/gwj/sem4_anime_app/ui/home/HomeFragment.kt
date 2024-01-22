package com.gwj.sem4_anime_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gwj.sem4_anime_app.ui.base.BaseFragment
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
        ViewCompat.requestApplyInsets(binding.coordinator)
        super.setupUIComponents()
        setupTopAnimeAdapter()
        setupRecommendedAnimeAdapter(true)

        //================ toggle button Start ==================
        binding.toggleBtnGrid.setOnClickListener {
            viewModel.setGridView()
        }

        binding.toggleBtnLinear.setOnClickListener {
            viewModel.setLinearView()
        }
        //================ toggle button End ==================
    }


    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        //===================== lifecycleScope topanimes Start =====================
        lifecycleScope.launch {
            viewModel.topAnimes.collect {
                HorizontalTopAnimeAdapter.setTopAnimes(it)
            }
        }
        //===================== lifecycleScope topanimes End =====================

        //====================== lifecycleScope SeasonNow Start ==================
        lifecycleScope.launch {
            viewModel.seasonNowAnimes.collect {
                // if list is not empty, hide the progress bar
                if (it.isNotEmpty()){
                    binding.progressBar.visibility = View.GONE
                    binding.toggleGroup.visibility = View.VISIBLE
                    binding.line001.visibility = View.VISIBLE
                    SeasonNowAnimeAdapter.baseSetSeasonNowAnimes(it)
                }else{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.toggleGroup.visibility = View.GONE
                    binding.line001.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.toggleIsGridOrLinear.collect {
                setupRecommendedAnimeAdapter(it.first, it.second)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                // if isLoading is false mean the data is finish fetching, hide the progress bar
                if (isLoading.not()) {
                    binding.myDotLoading.visibility = View.GONE
                }else{
                    binding.myDotLoading.visibility = View.VISIBLE
                }
            }
        }
        //====================== lifecycleScope SeasonNow End ==================

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
        //if user click on grid button, then change the adapter to grid layout else linear layout
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

        /**
         * Endless Scrolling
         * OnScrollListener it use for recyclerview, ut are use to monitor the scroll
         * then we only can use this to trigger the loadMoreItems function
         * When user reach the last 2 item,will trigger loadMoreItems function
         */
        binding.verticalAnimeRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //try catch to handle both linear and grid layout manager
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
}