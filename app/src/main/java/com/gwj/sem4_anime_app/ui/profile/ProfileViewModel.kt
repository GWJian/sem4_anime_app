package com.gwj.sem4_anime_app.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.sem4_anime_app.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.core.services.StorageService
import com.gwj.sem4_anime_app.data.model.Users
import com.gwj.sem4_anime_app.data.repo.user.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UsersRepo,
    private val storageService: StorageService
): BaseViewModel() {

    private val _user = MutableStateFlow(Users(username = "Unknown", email = "Unknown"))
    val user: StateFlow<Users> = _user

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish

    init {
        getCurrentUser()
        getProfilePicUri()
    }

    fun logout() {
        authService.logOut()
        viewModelScope.launch {
            _finish.emit(Unit)
        }
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

    fun updateProfilePic(uri: Uri) {
        user.value.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val name = "$it.jpg"
                storageService.imageAdd(name, uri)
                getProfilePicUri()
            }
        }
    }

    fun getProfilePicUri() {
        viewModelScope.launch(Dispatchers.IO) {
            authService.getCurrentUser()?.uid?.let {
                _profileUri.value = storageService.imageGet("$it.jpg")
            }
        }
    }

}