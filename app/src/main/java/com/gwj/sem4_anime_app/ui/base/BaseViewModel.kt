package com.gwj.recipesapp.ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel:ViewModel() {
    protected val _error:MutableSharedFlow<String> = MutableSharedFlow()
    val error:SharedFlow<String> = _error

    protected val _success:MutableSharedFlow<String> = MutableSharedFlow()
    val success: SharedFlow<String> = _success

    protected val _isFetchingData: MutableSharedFlow<Boolean>  = MutableSharedFlow()
    val isFetchingData: SharedFlow<Boolean> = _isFetchingData

    protected val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isLoading: MutableStateFlow<Boolean> = _isLoading

    protected val _noData: MutableSharedFlow<Boolean> = MutableSharedFlow()
    var noData: SharedFlow<Boolean> = _noData

    open fun onCreate(){}

    suspend fun <T> safeApiCall(callback: suspend () -> T?): T? {
        return try {
            callback()
        } catch (e: Exception) {
            //throw e
            _error.emit(e.message ?: "Something went wrong")
            null
        }
    }
}