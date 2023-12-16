package com.gwj.sem4_anime_app.ui.home

import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.model.DataX
import com.gwj.sem4_anime_app.data.repo.genres.GenresRepo
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val AllAnimeRepo: AnimeRepo,
    private val AllGenresRepo: GenresRepo,

    ) : BaseViewModel() {
    protected val _animes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val animes: StateFlow<List<Data>> = _animes
    protected val _genres: MutableStateFlow<List<DataX>> = MutableStateFlow(emptyList())
    val genres: StateFlow<List<DataX>> = _genres

    init {
        getAllAnimes()
        getAllGenres()
    }

    private fun getAllAnimes() {
        viewModelScope.launch {
            try {
                AllAnimeRepo.getTopAnimeList().let {
                    _animes.value = it
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Something went wrong")
            }
        }
    }

    private fun getAllGenres() {
        viewModelScope.launch {
            try {
                AllGenresRepo.getAllGenres().let {
                    _genres.value = it
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Something went wrong")
            }

        }
    }
}