package com.gwj.sem4_anime_app.ui.reset_password

import androidx.lifecycle.viewModelScope
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPassViewModel @Inject constructor(
    private val authService: AuthService,
) : BaseViewModel() {

    fun resetPass(email: String) {
        viewModelScope.launch(Dispatchers.IO) {

            _success.emit("Email Sent")
            authService.resetPass(email)

//            val resetEmail = safeApiCall {
//                authService.resetPass(email)
//            }
//
//            if(resetEmail != null) {
//                _success.emit("Email sent")
//            } else {
//                _error.emit("Invalid Email")
//            }
        }
    }


}