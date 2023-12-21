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
    protected val _animes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val animes: StateFlow<List<Data>> = _animes

    init {
        getTopAnimes()
    }

    private fun getTopAnimes() {
        viewModelScope.launch {
            try {
                AllAnimeRepo.getTopAnimeList().let {
                    _animes.value = it
                    Log.d("debugging_HomeViewModel", "getTopAnimes: $it")
                    Log.d("debugging_HomeViewModel", "Number of items: ${_animes.value.size}")
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Something went wrong")
            }
        }
    }

}