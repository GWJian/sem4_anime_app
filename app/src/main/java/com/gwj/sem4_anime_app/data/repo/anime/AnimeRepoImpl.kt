package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.Data

class AnimeRepoImpl(private val AnimeApi: AnimeApi) : AnimeRepo {

    //get all top anime
    override suspend fun getTopAnimeList(): List<Data> {
        return AnimeApi.getTopAnime().animeData ?: emptyList()
    }

    override suspend fun getSeasonalAnime(): List<Data> {
        return AnimeApi.getSeasonalAnime().animeData ?: emptyList()
    }

    override suspend fun getAnimeById(id: Int): Data? {
        TODO("Not yet implemented")
    }
}