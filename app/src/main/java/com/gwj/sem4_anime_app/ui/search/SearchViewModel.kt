package com.gwj.sem4_anime_app.ui.search

import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val Animes: AnimeRepo,
) : BaseViewModel() {
    protected val _searchAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val searchAnimes: MutableStateFlow<List<Data>> = _searchAnimes

    //Job to stop the search when user is typing too fast
    var searchJob: Job? = null
    var currentPage = 1
    var isLoading = false

    init {
        getAllAnimes()
        searchAnime("")
    }

    //show anime without searching anything
    private fun getAllAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                Animes.searchAnime("").let {
                    _searchAnimes.value = it
                }
            }
        }
    }

    //target the anime that we want to search
    //we use Job to prevent user from typing too fast and keep searching and cause 429 - Too Many Request
    fun searchAnime(query: String?) {
        searchJob?.cancel() //cancel to prevent user from typing too fast.
        if (!query.isNullOrBlank()) {
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(300) //delay use to control the rate of the request if user is typing too fast
                safeApiCall {
                    Animes.searchAnime(query).let {
                        _searchAnimes.value = it
                    }
                }
            }
        }
    }

    /**
     * Loads more items when the user scrolls to the end of the list.
     * Join Two List
     * TODO: if possible, add pagination to control is there any more data to load
     */
    fun loadMoreItems() {
        // check are we loading the data
        if (!isLoading) {
            // if true, let the page increment and load new data into the list
            isLoading = true
            // page +1
            currentPage++
            viewModelScope.launch(Dispatchers.IO) {
                delay(1000) //delay to control the rate of the request
                safeApiCall {
                    Animes.searchAnime("", currentPage).let { newItems ->
                        // get current anime list
                        val currentItems = _searchAnimes.value
                        // add new list to current list => join two list
                        _searchAnimes.value = currentItems + newItems
                        // if done loading, set it back to false so it can prevent user from keep scrolling and get 429 RateLimitException error warning
                        isLoading = false
                    }
                }
            }
        }
    }

}