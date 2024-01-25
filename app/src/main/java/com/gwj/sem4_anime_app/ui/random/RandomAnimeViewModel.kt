package com.gwj.sem4_anime_app.ui.random

import androidx.lifecycle.viewModelScope
import com.gwj.sem4_anime_app.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RandomAnimeViewModel @Inject constructor(
    private val randomAnimeRepo: AnimeRepo,
) : BaseViewModel() {
    protected val _randomAnime: MutableStateFlow<Data?> = MutableStateFlow(null)
    val randomAnime: StateFlow<Data?> = _randomAnime

    val randomAnimeId = Random.nextInt(1000, 5000) //just a function to generate random int 1 to 1k

    init {
        getAnime()
    }

    private fun getAnime() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                randomAnimeRepo.getDetailAnime(randomAnimeId).let { anime ->
                    _randomAnime.value = anime
                }
            }
        }
    }


    fun getRandomAnime() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                randomAnimeRepo.getRandomAnime().let { randomAnime ->
                    _randomAnime.value = randomAnime
                }
            }
        }
    }

}