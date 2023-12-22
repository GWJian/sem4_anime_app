package com.gwj.sem4_anime_app.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.databinding.FragmentHomeBinding
import com.gwj.sem4_anime_app.databinding.FragmentSearchBinding
import com.gwj.sem4_anime_app.ui.adapter.SearchAnimeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = SearchAnimeAdapter(emptyList())

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchAnimeRecyclerView.adapter = adapter
        binding.searchAnimeRecyclerView.layoutManager = layoutManager
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.searchAnimes.collect() {
                adapter.setSearchAnimes(it)
            }
        }

    }


}