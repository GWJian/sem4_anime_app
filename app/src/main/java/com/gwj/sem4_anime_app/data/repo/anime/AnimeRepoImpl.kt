package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.Data

class AnimeRepoImpl(private val animeApi: AnimeApi) : AnimeRepo {
    override suspend fun getTopAnimeList(): List<Data> {
        return animeApi.getTopAnime().data ?: emptyList()
    }

    override suspend fun getSeasonNowAnime(page: Int): List<Data> {
        return animeApi.getSeasonNowAnime(page).data ?: emptyList()
    }

    override suspend fun getDetailAnime(animeId: Int): Data {
        val anime = animeApi.getDetailAnime(animeId)
        //Log.d("debugging_AnimeRepoImpl", "getDetailAnime: $anime")
        return anime.data
    }

    override suspend fun searchAnime(query: String, page: Int): List<Data> {
        return animeApi.searchAnime(query, page).data ?: emptyList()
    }

    override suspend fun getRandomAnime(): Data {
        return animeApi.getRandomAnime().data
    }

    override suspend fun getSeasonalAnime(year: String, season: String,page: Int): List<Data> {
        return animeApi.getSeasonalAnime(year, season, page).data ?: emptyList()
    }


}