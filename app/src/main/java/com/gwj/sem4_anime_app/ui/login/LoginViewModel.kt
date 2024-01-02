package com.gwj.sem4_anime_app.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.core.services.AuthService
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
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val usersRepo: UsersRepo
) : BaseViewModel() {

//    private val _navToTab = MutableSharedFlow<Unit>()
//    val navToTab: SharedFlow<Unit> get() = _navToTab

    private val _user = MutableStateFlow(Users(username = "Unknown", useremail = "Unknown"))
    val user: StateFlow<Users> = _user



     fun login(useremail:String, password:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = safeApiCall {
                authService.login(useremail,password)
            }
            if(res != null) {
                _success.emit("Login Success")
            }
        }



    }

    fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        Log.d("debugging", firebaseUser?.uid.toString())
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                safeApiCall { usersRepo.userGet(it.uid) }?.let {  user ->
                    Log.d("debugging", user.toString())
                    _user.value = user
                }
            }
        }
    }
}