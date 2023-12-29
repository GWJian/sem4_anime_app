package com.gwj.sem4_anime_app.ui.seasonal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.FragmentSeasonalBinding
import com.gwj.sem4_anime_app.ui.adapter.SeasonalAdapter
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeasonalFragment : BaseFragment<FragmentSeasonalBinding>() {

    override val viewModel: SeasonalViewModel by viewModels()
    private lateinit var seasonalAdapter: SeasonalAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeasonalBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun setupUIComponents() {
        super.setupUIComponents()
        setupSeasonalAdapter()
        setupYearForAutoCompleteTextView()
        setupSeasonalForAutoCompleteTextView()
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
    }

    private fun setupYearForAutoCompleteTextView() {
        //set array to AutoCompleteTextView
        //{it.toString()} this will convert int to string
        val years = (2024 downTo 1927).map { it.toString() }.toTypedArray()
        val yearAdapter =
            ArrayAdapter(
                requireContext(),
                androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                years
            )
        binding.ACTVYear.setAdapter(yearAdapter)
        //pass selected data to viewModel
        //setOnItemClickListener cuz we want to get selected list item
        binding.ACTVYear.setOnItemClickListener { _, _, position, _ ->
            //position => to get years array position,[0,1,2,...]
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

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.seasonalAnimes.collect() {
                seasonalAdapter.setSeasonalAnimes(it)
            }
        }

    }


}