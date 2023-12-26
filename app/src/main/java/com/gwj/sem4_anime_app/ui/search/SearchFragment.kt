package com.gwj.sem4_anime_app.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.FragmentSearchBinding
import com.gwj.sem4_anime_app.ui.adapter.SearchAnimeAdapter
import com.gwj.sem4_anime_app.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
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
        adapter.listener = object : SearchAnimeAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(animeId.mal_id.toString())
                navController.navigate(action)
            }
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchAnimeRecyclerView.adapter = adapter
        binding.searchAnimeRecyclerView.layoutManager = layoutManager

        // Load more items when the user scrolls to the end of the list.
        binding.searchAnimeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            // this will triggered when the user scrolls to the end of the list
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // get the current visible item position in the list
                val myLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                // total item count
                val totalItemCount = myLayoutManager.itemCount
                // last visible item position
                val lastVisibleItem = myLayoutManager.findLastVisibleItemPosition()

                // when user reach end of the list(5 items before the end of the list)
                // load the next page data
                if (totalItemCount <= lastVisibleItem + 5) {
                    viewModel.loadMoreItems()
                }
            }
        })

        // search function
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchAnime(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchAnime(newText)
                return true
            }
        })

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.searchAnimes.collect {
                adapter.setSearchAnimes(it)
            }
        }

    }


}