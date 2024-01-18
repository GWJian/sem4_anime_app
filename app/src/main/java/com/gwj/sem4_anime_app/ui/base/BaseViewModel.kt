package com.gwj.recipesapp.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel:ViewModel() {
    protected val _error:MutableSharedFlow<String> = MutableSharedFlow()
    val error:SharedFlow<String> = _error

    protected val _success:MutableSharedFlow<String> = MutableSharedFlow()
    val success: SharedFlow<String> = _success

    suspend fun <T> safeApiCall(callback: suspend () -> T?): T? {
        return try {
            callback()
        } catch (e: Exception) {
//            throw e
            e.printStackTrace()
            Log.d("testconnect", e.toString())
            _error.emit(e.message ?: "Something went wrong")
            null
        }
    }
}