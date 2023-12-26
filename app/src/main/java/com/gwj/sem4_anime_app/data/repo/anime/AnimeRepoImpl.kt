package com.gwj.sem4_anime_app.data.repo.anime

import android.util.Log
import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.Data

class AnimeRepoImpl(private val animeApi: AnimeApi) : AnimeRepo {

    override suspend fun getTopAnimeList(): List<Data> {
        return animeApi.getTopAnime().data ?: emptyList()
    }

    override suspend fun getSeasonNowAnime(): List<Data> {
        return animeApi.getSeasonNowAnime().data ?: emptyList()
    }

    override suspend fun getDetailAnime(animeId: Int): Data {
        val anime = animeApi.getDetailAnime(animeId)
        //Log.d("debugging_AnimeRepoImpl", "getDetailAnime: $anime")
        return anime.data
    }

    override suspend fun searchAnime(query: String, page: Int): List<Data> {
        return animeApi.searchAnime(query, page = page).data ?: emptyList()
    }

    override suspend fun getRandomAnime(): Data {
        return animeApi.getRandomAnime().data
    }


//    override suspend fun getSeasonalAnime(year: String, season: String): List<AnimeData> {
//        return animeApi.getSeasonalAnime(year, season).data ?: emptyList()
//    }



}