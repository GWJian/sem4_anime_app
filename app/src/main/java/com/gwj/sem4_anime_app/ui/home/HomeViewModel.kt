package com.gwj.sem4_anime_app.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val Animes: AnimeRepo,
) : BaseViewModel() {
    protected val _topAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val topAnimes: StateFlow<List<Data>> = _topAnimes

    protected val _seasonNowAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val seasonNowAnimes: StateFlow<List<Data>> = _seasonNowAnimes

    var currentPage = 1
    var isLoading = false

    init {
        getTopAnimes()
        getSeasonNowAnimes()
    }

    private fun getTopAnimes() {
        viewModelScope.launch {
            safeApiCall {
                Animes.getTopAnimeList().let {
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
                Animes.getSeasonNowAnime().let {
                    _seasonNowAnimes.value = it
                }
            }
        }
    }

    fun loadMoreItems() {
        if (!isLoading) {

            isLoading = true

            currentPage++
            viewModelScope.launch(Dispatchers.IO) {
                delay(1000)
                safeApiCall {
                    Animes.getSeasonNowAnime(currentPage).let { newAnime ->
                        val currentAnimes = _seasonNowAnimes.value
                        _seasonNowAnimes.value = currentAnimes + newAnime
                        isLoading = false
                    }
                }
            }

        }
    }

}