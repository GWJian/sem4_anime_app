package com.gwj.sem4_anime_app.data.repo.user

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.Users
import kotlinx.coroutines.tasks.await

class UsersRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): UsersRepo {
    private fun dbRefGet(): CollectionReference {
        return db.collection("users")
    }

    private fun uidGet(): String{
        val firebaseUser = authService.getCurrentUser()

        return firebaseUser?.uid ?: throw Exception("Unauthorized Action")
    }

    override suspend fun userAdd(user: Users) {
        dbRefGet().document(uidGet()).set(user.toHashMap()).await()

//        dbRefGet().document(uidGet()).get().await()
//        dbRefGet().document(uidGet()).set(user.toHashMap()).await()

    }

    override suspend fun userGet(uid: String): Users? {
        val document = dbRefGet().document(uidGet()).get().await()

        return document.data?.let {
            it["id"] = uidGet()
            Users.fromHashMap(it)
        }
    }

    override suspend fun userRemove() {
        dbRefGet()
    }

    override suspend fun userUpdate(users: Users) {
        dbRefGet().document(uidGet()).set(users.toHashMap())
    }

}