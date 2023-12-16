package com.gwj.sem4_anime_app.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.AnimeData
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.model.DataX
import com.gwj.sem4_anime_app.data.repo.genres.GenresRepo
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import com.gwj.sem4_anime_app.data.repo.random.RandomAnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val AllAnimeRepo: AnimeRepo,
    private val AllGenresRepo: GenresRepo,
    private val RandomAnimeRepo: RandomAnimeRepo,
    ) : BaseViewModel() {
    protected val _animes: MutableStateFlow<List<AnimeData>> = MutableStateFlow(emptyList())
    val animes: StateFlow<List<AnimeData>> = _animes
    protected val _genres: MutableStateFlow<List<DataX>> = MutableStateFlow(emptyList())
    val genres: StateFlow<List<DataX>> = _genres
    protected val _randomAnime: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val randomAnime: StateFlow<List<Data>> = _randomAnime

    init {
        //getAllAnimes()
        //getAllGenres()
        //getRandomAnime()
        //getDetailAnime(35247) //TODO ask sir,how to put empty num,if string is "",num put 0?
        getSeonalAnime("2021", "summer")
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

    private fun getRandomAnime() {
        viewModelScope.launch {
            try {
                RandomAnimeRepo.getRandomAnime().let {
                    _randomAnime.value = listOf(it)
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Something went wrong")
            }

        }
    }

    private fun getDetailAnime(id: Int) {
        viewModelScope.launch {
            try {
                AllAnimeRepo.getDetailAnime(id).let {
                    _randomAnime.value = listOf(it)
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Something went wrong")
            }
        }
    }

    private fun getSeonalAnime(year: String, season: String) {
        viewModelScope.launch {
            try {
                AllAnimeRepo.getSeasonalAnime(year, season).let {
                    _animes.value = it
                }
            } catch (e: Exception) {
                _error.emit(e.message ?: "Something went wrong")
            }
        }
    }

}