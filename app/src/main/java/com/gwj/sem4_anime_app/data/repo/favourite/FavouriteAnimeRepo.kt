package com.gwj.sem4_anime_app.data.repo.favourite

import com.gwj.sem4_anime_app.data.model.FavouriteAnime
import kotlinx.coroutines.flow.Flow

interface FavouriteAnimeRepo {

    fun getAllFavouriteAnime(uid: String): Flow<List<FavouriteAnime>>

    suspend fun getFavAnimeById(id: String): FavouriteAnime?

    suspend fun addToFavourite(animeId: String, favId: FavouriteAnime)

    suspend fun removeFromFavourite(animeId: String)


}