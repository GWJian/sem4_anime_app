package com.gwj.sem4_anime_app.ui.seasonal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.AnimeResp
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonalViewModel @Inject constructor(
    private val SeasonalAnimes: AnimeRepo,
) : BaseViewModel() {
    protected val _seasonalAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val seasonalAnimes: MutableStateFlow<List<Data>> = _seasonalAnimes

    var currentPage = 1
    var isLoading = false

    init {
        getSeasonalAnimes()
    }

    private fun getSeasonalAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                SeasonalAnimes.getSeasonalAnime("2020", "spring").let {
                    _seasonalAnimes.value = it
                }
            }
        }
    }


}