package com.gwj.sem4_anime_app.data.repo.favourite

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.FavouriteAnime
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FavouriteAnimeRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): FavouriteAnimeRepo {

    private fun getDbRef(): CollectionReference {
        return db.collection("Favourites/${getUid()}/animes")
    }

    private fun getUid(): String {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid ?: throw Exception("No authorized user found")
    }


    override fun getAllFavouriteAnime(uid: String) = callbackFlow {
        val listener = getDbRef().addSnapshotListener { value, error ->
            if (error != null) {
                throw error
            }

            val animes = mutableListOf<FavouriteAnime>()
            value?.documents?.let { docs ->
                for (doc in docs) {
                    doc.data?.let {
                        it["id"] = doc.id
                        animes.add(FavouriteAnime.fromHashMap(it))
                    }
                }
                trySend(animes)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getFavAnimeById(id: String): FavouriteAnime? {
        val res = getDbRef().document(id).get().await()
        return res.data?.let { FavouriteAnime.fromHashMap(it) }
    }


    override suspend fun addToFavourite(animeId: String, favId: FavouriteAnime) {
        getDbRef().document(animeId).set(favId)
    }

    override suspend fun removeFromFavourite(animeId: String) {
        getDbRef().document(animeId).delete().await()
    }


}