package com.gwj.sem4_anime_app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val Animes: AnimeRepo,
) : BaseViewModel() {
    protected val _searchAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val searchAnimes: MutableStateFlow<List<Data>> = _searchAnimes

    init {
        getAllAnimes()
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



}