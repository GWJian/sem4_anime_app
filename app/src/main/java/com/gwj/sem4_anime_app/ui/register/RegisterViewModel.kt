package com.gwj.sem4_anime_app.ui.register

import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.Users
import com.gwj.sem4_anime_app.data.repo.user.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


@HiltViewModel
class RegisterViewModel(
    private val authService: AuthService,
    private val usersRepo: UsersRepo
) : BaseViewModel() {
    fun signUp(username: String, useremail: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            val user = safeApiCall {
                authService.register(useremail, password)
            }

            if(user != null) {
                _success.emit("Registered")
                safeApiCall {
                    usersRepo.userAdd(
                        Users(username = username, useremail = useremail)
                    )
                }
            }
        }
    }
}