package com.gwj.sem4_anime_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.FragmentHomeBinding
import com.gwj.sem4_anime_app.ui.adapter.HorizontalTopAnimeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var HorizontalTopAnimeAdapter: HorizontalTopAnimeAdapter

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
    }

    private fun setupTopAnimeAdapter() {
        HorizontalTopAnimeAdapter = HorizontalTopAnimeAdapter(emptyList())
        HorizontalTopAnimeAdapter.listener = object : HorizontalTopAnimeAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action =
                    HomeFragmentDirections.homeFragmentToContentFragment(animeId.mal_id.toString())
                navController.navigate(action)
            }
        }
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalTopAnimeRecycleView.adapter = HorizontalTopAnimeAdapter
        binding.horizontalTopAnimeRecycleView.layoutManager = layoutManager
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

    }
}