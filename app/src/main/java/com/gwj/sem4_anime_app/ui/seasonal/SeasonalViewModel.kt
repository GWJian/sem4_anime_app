package com.gwj.sem4_anime_app.ui.seasonal

import androidx.lifecycle.viewModelScope
import com.gwj.sem4_anime_app.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonalViewModel @Inject constructor(
    private val SeasonalAnimes: AnimeRepo,
) : BaseViewModel() {
    private val _seasonalAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val seasonalAnimes: MutableStateFlow<List<Data>> = _seasonalAnimes

    var year = "2020"
    var season = "spring"
    var currentPage = 1

    init {
        getSeasonalAnimes()
    }

    fun updateSeasonalAnimes(year: String, season: String) {
        this.year = year
        this.season = season
        getSeasonalAnimes()
    }

    private fun getSeasonalAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            _isFetchingData.emit(true)
            safeApiCall {
                SeasonalAnimes.getSeasonalAnime(year, season).let {
                    _seasonalAnimes.value = it
                    _isFetchingData.emit(false)
                }
            }
        }
    }

    fun loadMoreItems() {
        if (!_isLoading.value) {
            _isLoading.value = true
            currentPage++
            viewModelScope.launch(Dispatchers.IO) {
                delay(1000)
                safeApiCall {
                    SeasonalAnimes.getSeasonalAnime(year, season, currentPage).let { anime ->
                        val currentAnimes = _seasonalAnimes.value
                        _seasonalAnimes.value = currentAnimes + anime
                        _isLoading.value = false
                    }
                }
            }
        }
    }

}