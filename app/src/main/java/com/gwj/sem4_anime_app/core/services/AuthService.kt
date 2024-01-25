package com.gwj.sem4_anime_app.core.services

import com.google.firebase.auth.FirebaseUser

interface AuthService
{
    suspend fun register(
        email:String,
        password:String
    ): FirebaseUser?

    suspend fun login(
        email:String,
        password:String
    ): FirebaseUser?


    fun getCurrentUser(): FirebaseUser?

    fun logOut()

    fun resetPass(email: String)
}