package com.gwj.sem4_anime_app.ui.seasonal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.FragmentSeasonalBinding
import com.gwj.sem4_anime_app.ui.adapter.SeasonalAdapter
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeasonalFragment : BaseFragment<FragmentSeasonalBinding>() {

    override val viewModel: SeasonalViewModel by viewModels()
    private lateinit var seasonalAdapter: SeasonalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(
                Lifecycle.State.RESUMED
            ) {
                setupYearForAutoCompleteTextView()
                setupSeasonalForAutoCompleteTextView()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeasonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        ViewCompat.requestApplyInsets(binding.coordinator)
        super.setupUIComponents()
        setupSeasonalAdapter()
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.seasonalAnimes.collect {
                if (it.isNotEmpty()) {
                    binding.progressBar.visibility = View.GONE
                    seasonalAdapter.setSeasonalAnimes(it)
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                // not() = false
                if (isLoading.not()) {
                    binding.myDotLoading.visibility = View.GONE
                }else{
                    binding.myDotLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupSeasonalAdapter() {
        seasonalAdapter = SeasonalAdapter(emptyList())
        seasonalAdapter.listener = object : SeasonalAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(
                        animeId.mal_id.toString()
                    )
                navController.navigate(action)
            }
        }
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.seasonalAnimeRecycleView.adapter = seasonalAdapter
        binding.seasonalAnimeRecycleView.layoutManager = layoutManager

        binding.seasonalAnimeRecycleView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val seasonalLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = seasonalLayoutManager.itemCount
                val lastAnime = seasonalLayoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= lastAnime + 1) {
                    viewModel.loadMoreItems()
                }

            }

        })
    }

    private fun setupYearForAutoCompleteTextView() {
        //set array to AutoCompleteTextView
        //{it.toString()} this will convert int to string, toTypedArray = toArray
        val years = (2024 downTo 1927).map { it.toString() }.toTypedArray()
        val yearAdapter =
            ArrayAdapter(
                requireContext(),
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                years
            )
        binding.ACTVYear.setAdapter(yearAdapter)
        //pass selected data to viewModel
        //setOnItemClickListener cuz using AutoCompleteTextView
        binding.ACTVYear.setOnItemClickListener { _, _, position, _ ->
            val selectedYear = years[position]
            //selectedYear => user selected year, viewModel.season => get season from viewModel
            viewModel.updateSeasonalAnimes(selectedYear, viewModel.season)
        }
    }

    private fun setupSeasonalForAutoCompleteTextView() {
        //set array to AutoCompleteTextView
        val seasonal = arrayOf("Spring", "Summer", "Fall", "Winter")
        val seasonalAdapter =
            ArrayAdapter(
                requireContext(),
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                seasonal
            )
        binding.ACTVseasonal.setAdapter(seasonalAdapter)

        //pass selected data to viewModel
        binding.ACTVseasonal.setOnItemClickListener { _, _, position, _ ->
            val selectedSeason = seasonal[position]
            //viewModel.year => get year from viewModel, selectedSeason => user selected season
            viewModel.updateSeasonalAnimes(viewModel.year, selectedSeason)
        }
    }


}