package com.gwj.sem4_anime_app.data.repo.anime

import android.util.Log
import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.Data
import retrofit2.http.GET

class AnimeRepoImpl(private val animeApi: AnimeApi) : AnimeRepo {

    //use AnimeData cuz need pagination to handle the pagination of the data
    override suspend fun getTopAnimeList(): List<Data> {
        return animeApi.getTopAnime().data ?: emptyList()
    }
//    override suspend fun getTopAnimeList(): List<Data> {
//        val response = animeApi.getTopAnime()
//        Log.d("debugging_AnimeRepoImpl", "Raw API response: $response")
//        val animeData = response.data
//        Log.d("debugging_AnimeRepoImpl", "Parsed anime data: $animeData")
//        return animeData ?: emptyList()
//    }

//    override suspend fun getSeasonNowAnime(): List<AnimeData> {
//        return animeApi.getSeasonNowAnime().data ?: emptyList()
//    }

//    override suspend fun getSeasonalAnime(year: String, season: String): List<AnimeData> {
//        return animeApi.getSeasonalAnime(year, season).data ?: emptyList()
//    }

    //use Data cuz no pagination only 1 anime
//    override suspend fun getDetailAnime(id: Int): Data {
//        return animeApi.getDetailAnime(id)
//    }


}