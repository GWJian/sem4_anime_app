package com.gwj.sem4_anime_app.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.AnimeResp
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.model.DataX
import com.gwj.sem4_anime_app.data.model.data.Pagination
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val Animes: AnimeRepo,
) : BaseViewModel() {
    protected val _searchAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val searchAnimes: MutableStateFlow<List<Data>> = _searchAnimes
    protected val _animeGenres: MutableStateFlow<List<DataX>> = MutableStateFlow(emptyList())
    val animeGenres: MutableStateFlow<List<DataX>> = _animeGenres

    //Job to stop the search when user is typing too fast
    var searchJob: Job? = null
    var currentPage = 1

    /**
     * we need this to store the current query,if we pass empty string "", after search result reach end, then it will back to "" and show all anime again
     * let say https://api.jikan.moe/v4/anime?q=overlord&sfw=true&page=1&limit=25 {"pagination":{"last_visible_page":1,"has_next_page":false,"current_page":1,"items":{"count":19,"total":19,"per_page":25}}
     * after we reach the end,it will auto go back https://api.jikan.moe/v4/anime?q=&sfw=true&page=1&limit=25 to show all data again
     */
    var currentQuery = ""
    var currentGenresId = ""

    init {
        getAllAnimes()
        getAnimeGenres()
        searchAnime("", "")
    }

    //show default anime setting
    private fun getAllAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            _isFetchingData.emit(true)
            safeApiCall {
                Animes.searchAnime("", "").let {
                    _searchAnimes.value = it
                    _isFetchingData.emit(false)
                }
            }
        }
    }

    private fun getAnimeGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            _isFetchingData.emit(true)
            safeApiCall {
                Animes.getAnimeGenres().let {
                    _animeGenres.value = it
                    _isFetchingData.emit(false)
                }
            }
        }
    }

    //target the anime that we want to search
    //we use Job to prevent user from typing too fast and keep searching and cause 429 - Too Many Request
    fun searchAnime(genres: String, query: String?) {
        //Log.d("debugging_SearchViewModel", "Genres ID: $genres, Query: $query")
        searchJob?.cancel() //cancel to prevent user from typing too fast.

        if (!query.isNullOrBlank() || genres.isNotBlank() || genres.isBlank()) {
            currentGenresId = genres //Store the current genres
            currentQuery = query!! // Store the current query
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(300) //delay use to control the rate of the request if user is typing too fast
                safeApiCall {
                    Animes.searchAnime(genres, query).let {
                        _searchAnimes.value = it
                        if (_searchAnimes.value.isEmpty()) {
                            _noData.emit(true)
                            Log.d("debugging_SearchViewModel", "No Data")
                        } else {
                            _noData.emit(false)
                            Log.d("debugging_SearchViewModel", "Have Data")
                        }
                    }
                }
            }
        }
    }

    /**
     * Loads more items when the user scrolls to the end of the list.
     * Join Two List
     */
    fun loadMoreItems() {
        // if isLoading is false and has_next_page is true run the code
        if (!_isLoading.value) {
            // if true, let the page increment and load new data into the list
            _isLoading.value = true
            // page +1.
            currentPage++
            viewModelScope.launch(Dispatchers.IO) {
                delay(1000) //delay to control the rate of the request
                safeApiCall {
                    Animes.searchAnime(currentGenresId, currentQuery, currentPage).let { newItems ->
                        // get current loaded list from _searchAnimes
                        val currentItems = _searchAnimes.value
                        // add newItems list to currentItems list => join two list
                        _searchAnimes.value = currentItems + newItems
                        // if done loading, set it back to false so it can prevent user from keep scrolling and get 429 RateLimitException error warning
                        _isLoading.value = false
                    }
                }
            }
        }
    }

}