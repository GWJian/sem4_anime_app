package com.gwj.sem4_anime_app.ui.search

import android.app.AlertDialog
import android.content.DialogInterface
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Arrays

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val viewModel: SearchViewModel by viewModels()
    private lateinit var SearchAnimeAdapter: SearchAnimeAdapter

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
        SearchAnimeAdapter = SearchAnimeAdapter(emptyList())
        SearchAnimeAdapter.listener = object : SearchAnimeAdapter.Listener {
            override fun onClick(animeId: Data) {
                val action =
                    TabContainerFragmentDirections.actionTabContainerFragmentToContentFragment(
                        animeId.mal_id.toString()
                    )
                navController.navigate(action)
            }
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchAnimeRecyclerView.adapter = SearchAnimeAdapter
        binding.searchAnimeRecyclerView.layoutManager = layoutManager

        // Load more items when the user scrolls to the end of the list.
        binding.searchAnimeRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            // this will triggered when the user scrolls to the end of the list
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // get the current visible item position in the list
                val myLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                // total item count
                val totalItemCount = myLayoutManager.itemCount
                // last visible item position
                val lastVisibleAnimeItem = myLayoutManager.findLastVisibleItemPosition()

                // when user reach end of the list(5 items before the end of the list)
                // load the next page data
                if (totalItemCount <= lastVisibleAnimeItem + 5) {
                    viewModel.loadMoreItems()
                }
            }
        })

        // search function
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchAnime(viewModel.currentGenresId, query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchAnime(viewModel.currentGenresId, newText)
                return true
            }
        })

//        lifecycleScope.launch{
//            viewModel.searchAnimes.collect{
//                SearchAnimeAdapter.setSearchAnimes(it)
//            }
//        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        //========================== Search Anime =============================
        lifecycleScope.launch {
            viewModel.searchAnimes.collect {
                if (it.isNotEmpty()) {
                    binding.progressBar.visibility = View.GONE
                    SearchAnimeAdapter.setSearchAnimes(it)
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
        //========================== Search Anime =============================


        //========================== Anime Genres =============================
        lifecycleScope.launch {
            viewModel.animeGenres.collect { animeGenres ->
                // initialise the list items for the alert dialog
                val listItems = animeGenres.map { it.name }.toTypedArray()
                val checkedItems = BooleanArray(listItems.size)

                // copy the items from the main list to the selected item list for the preview
                val selectedGenres = mutableListOf<Int>()

                // handle the Open Alert Dialog button
                binding.popUpGenresBtn.setOnClickListener {
                    // initialise the alert dialog builder
                    val builder = AlertDialog.Builder(requireContext())

                    builder.setTitle("Choose Genres")
                        .setMultiChoiceItems(listItems, checkedItems) { _, which, isChecked ->
                            val genresId = animeGenres[which].mal_id
                            if (isChecked) {
                                selectedGenres.add(genresId)
                            } else {
                                selectedGenres.remove(genresId)
                            }
                            // Update the current focused item's checked status
                            checkedItems[which] = isChecked
                        }
                        // if user click yes,pass the checkedItems to viewModel.animeGenres
                        .setPositiveButton("OK") { _, _ ->
                            // Do something when click the positive button
                            val genresIdString = selectedGenres.joinToString(",")
                            viewModel.searchAnime(genresIdString, viewModel.currentQuery)
                        }
                        // if user click cancel,do ntg
                        .setNegativeButton("CANCEL") { _, _ -> }
                        // if user click CLEAR ALL,remove all checkedItems
                        .setNeutralButton("CLEAR ALL") { _: DialogInterface?, _: Int ->
                            Arrays.fill(checkedItems, false)
                            //clear the selectedGenres list and pass the empty list to viewModel.animeGenres
                            selectedGenres.clear()
                            viewModel.searchAnime("", viewModel.currentQuery)
                        }
                        .show()
                }
            }
        }
        //========================== Anime Genres =============================
    }


}