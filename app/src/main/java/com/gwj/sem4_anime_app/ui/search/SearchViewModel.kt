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
        searchJob?.cancel() //stop the previous search if user is typing too fast
        if (!query.isNullOrBlank()) {
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(300) //delay some ms to wait for user to finish typing
                safeApiCall {
                    Animes.searchAnime(query).let {
                        _searchAnimes.value = it
                    }
                }
            }
        }
    }


}