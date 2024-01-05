package com.gwj.sem4_anime_app.core.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthServiceImpl(
    val authenticate: FirebaseAuth = FirebaseAuth.getInstance()
): AuthService {
    override suspend fun register(email: String, password: String): FirebaseUser? {
        val res = authenticate.createUserWithEmailAndPassword(email,password).await()
        return res.user
    }

    override suspend fun login(email: String, password: String): FirebaseUser? {
        val res = authenticate.signInWithEmailAndPassword(email,password).await()
        return res.user
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authenticate.currentUser
    }

    override fun logOut() {
        authenticate.signOut()
    }
}