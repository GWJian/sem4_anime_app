package com.gwj.sem4_anime_app.ui.content

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.model.FavouriteAnime
import com.gwj.sem4_anime_app.data.model.Users
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import com.gwj.sem4_anime_app.data.repo.favourite.FavouriteAnimeRepo
import com.gwj.sem4_anime_app.data.repo.user.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UsersRepo,
    private val animeRepo: AnimeRepo,
    private val favouriteAnimeRepo: FavouriteAnimeRepo
) : BaseViewModel() {

    private val _user = MutableStateFlow(Users(username = "Unknown", email = "Unknown"))
    val user: StateFlow<Users> = _user

    private val _anime: MutableStateFlow<Data?> = MutableStateFlow(null)
    val anime: StateFlow<Data?> = _anime

    private val _favourite: MutableStateFlow<FavouriteAnime?> = MutableStateFlow(null)
    val favourite: StateFlow<FavouriteAnime?> = _favourite


    fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val user = safeApiCall { userRepo.userGet(it.uid) }
                    Log.d("debugging", "User information: $user")
                    user?.let { _user.value = it }
                } catch (e: Exception) {
                    Log.e("debugging", "Error fetching user information: $e")
                }
            }
        }
    }

    fun getAnimeDetail(animeId: Int) {
        getFavouriteAnime(animeId.toString())
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                animeRepo.getDetailAnime(animeId)?.let { animeDetail ->
                    _anime.value = animeDetail
                }
            }
        }
    }

    fun getFavouriteAnime(animeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _favourite.value = safeApiCall {
                favouriteAnimeRepo.getFavAnimeById(id = animeId)
            }
        }
    }


    fun addFavourite(animeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (anime.value != null) {
                safeApiCall {
                    favouriteAnimeRepo.addToFavourite(animeId, anime.value!!.toFavouriteAnime())
                }
            }
            getFavouriteAnime(animeId)
        }
    }

    fun removeFavourite(animeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                favouriteAnimeRepo.removeFromFavourite(animeId)
            }
            _favourite.value = null
        }
    }




}