package com.gwj.sem4_anime_app.ui.home

import androidx.lifecycle.viewModelScope
import com.gwj.sem4_anime_app.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val Animes: AnimeRepo,
) : BaseViewModel() {
    private val _topAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val topAnimes: StateFlow<List<Data>> = _topAnimes

    private val _seasonNowAnimes: MutableStateFlow<List<Data>> = MutableStateFlow(emptyList())
    val seasonNowAnimes: StateFlow<List<Data>> = _seasonNowAnimes

    /**
     * This is a pair of boolean and list of data
     * boolean are use for toggle between grid and linear layout
     * list of data are use for the list of anime when toggle is clicked
     */
    protected val _toggleIsGridOrLinear: MutableStateFlow<Pair<Boolean, List<Data>>> =
        MutableStateFlow(Pair(true, emptyList()))
    val toggleIsGridOrLinear: StateFlow<Pair<Boolean, List<Data>>> = _toggleIsGridOrLinear

    var currentPage = 1

    init {
        getTopAnimes()
        getSeasonNowAnimes()
    }

    private fun getTopAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            _isFetchingData.emit(true)
            safeApiCall {
                Animes.getSeasonNowAnime().let {
                    _seasonNowAnimes.value = it
                    _toggleIsGridOrLinear.value = Pair(true, it)
                    _isFetchingData.emit(false)
                }
            }
        }
    }

    fun loadMoreItems() {

        // checking is fetching data or not
        if (!_isLoading.value) {
            // if not fetching data, then set isLoading to true,thn will fire this function
            _isLoading.value = true
            //page +1
            currentPage++
            viewModelScope.launch(Dispatchers.IO) {
                delay(1000)
                safeApiCall {
                    //to get new anime from the next page
                    Animes.getSeasonNowAnime(currentPage).let { newAnime ->
                        val currentAnimes = _seasonNowAnimes.value
                        //add the new anime into the current anime list,so user can see both data instant replace it
                        _seasonNowAnimes.value = currentAnimes + newAnime
                        //after everything is done, set isLoading to false
                        _isLoading.value = false
                    }
                }
            }

        }
    }

    fun setGridView() {
        _toggleIsGridOrLinear.value = Pair(true,seasonNowAnimes.value)
    }

    fun setLinearView() {
        _toggleIsGridOrLinear.value = Pair(false,seasonNowAnimes.value)
    }

}