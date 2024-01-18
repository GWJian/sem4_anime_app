package com.gwj.sem4_anime_app.ui.register

import androidx.lifecycle.viewModelScope
import com.gwj.sem4_anime_app.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.Users
import com.gwj.sem4_anime_app.data.repo.user.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val usersRepo: UsersRepo
) : BaseViewModel() {
    fun signUp(username: String, email: String, password: String, confirmPassword: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val user = safeApiCall {
                authService.register(email, password)
            }

            if(user != null) {
                _success.emit("Registered")
                safeApiCall {
                    usersRepo.userAdd(
                        Users(username = username, email = email)
                    )
                }
            }
        }
    }
}