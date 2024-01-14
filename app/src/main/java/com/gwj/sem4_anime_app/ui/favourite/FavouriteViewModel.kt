package com.gwj.sem4_anime_app.ui.favourite

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.FavouriteAnime
import com.gwj.sem4_anime_app.data.model.Users
import com.gwj.sem4_anime_app.data.repo.favourite.FavouriteAnimeRepo
import com.gwj.sem4_anime_app.data.repo.user.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UsersRepo,
    private val favouriteAnimeRepo: FavouriteAnimeRepo
): BaseViewModel() {

    private val _user = MutableStateFlow(Users(username = "Unknown", email = "Unknown"))
    val user: StateFlow<Users> = _user

    private val _favourite: MutableStateFlow<List<FavouriteAnime>> = MutableStateFlow(emptyList())
    val favourite: StateFlow<List<FavouriteAnime>> = _favourite

    init {
        getCurrentUser()
        getAllFavouriteAnime()
    }

    fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
//                safeApiCall { userRepo.userGet(it.uid) }?.let { user ->
//                    _user.value = user
//                }
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

    fun getAllFavouriteAnime() {
        authService.getCurrentUser()?.uid?.let {id ->
            viewModelScope.launch(Dispatchers.IO) {
                safeApiCall {
                    favouriteAnimeRepo.getAllFavouriteAnime(id)
                }?.collect {
                    _favourite.value = it
                }
            }
        }
    }




}