package com.gwj.sem4_anime_app.ui.vidview

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

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val youtubeVideo: AnimeRepo,
) : BaseViewModel() {
    private val _animeVideo: MutableStateFlow<Data?> = MutableStateFlow(null)
    val animeVideo: StateFlow<Data?> = _animeVideo

    fun getAnimeVideo(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                youtubeVideo.getDetailAnime(animeId)?.let { animeDetail ->
                    _animeVideo.value = animeDetail
                }
            }
        }
    }

}