package com.gwj.sem4_anime_app.ui.seasonal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.databinding.FragmentSeasonalBinding
import com.gwj.sem4_anime_app.ui.adapter.SeasonalAdapter
import dagger.hilt.android.AndroidEntryPoint

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



}