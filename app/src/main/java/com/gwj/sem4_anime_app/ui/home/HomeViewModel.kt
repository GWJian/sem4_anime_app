package com.gwj.sem4_anime_app.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val AllAnimeRepo: AnimeRepo,
) : BaseViewModel() {
    protected val _topAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val topAnimes: StateFlow<List<Data>> = _topAnimes

    protected val _seasonNowAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val seasonNowAnimes: StateFlow<List<Data>> = _seasonNowAnimes


    init {
        getTopAnimes()
        getSeasonNowAnimes()
    }

    private fun getTopAnimes() {
        viewModelScope.launch {
            safeApiCall {
                AllAnimeRepo.getTopAnimeList().let {
                    _topAnimes.value = it
                    //Log.d("debugging_HomeViewModel", "getTopAnimes: $it")
                    //Log.d("debugging_HomeViewModel", "Number of items: ${_topAnimes.value.size}")
                }
            }
        }
    }

    private fun getSeasonNowAnimes() {
        viewModelScope.launch {
            safeApiCall {
                AllAnimeRepo.getSeasonNowAnime().let {
                    _seasonNowAnimes.value = it
                }
            }
        }
    }

}