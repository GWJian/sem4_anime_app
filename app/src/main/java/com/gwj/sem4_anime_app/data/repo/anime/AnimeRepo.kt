package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.model.AnimeResp
import com.gwj.sem4_anime_app.data.model.Data

interface AnimeRepo {

    suspend fun getTopAnimeList(): List<Data>

    suspend fun getDetailAnime(animeId: Int): Data?

    suspend fun searchAnime(query: String,page:Int = 1): List<Data>
    //suspend fun searchAnime(query: String,page:Int = 1): AnimeResp

    suspend fun getRandomAnime(): Data?

    //suspend fun getSeasonNowAnime(): List<AnimeData>

    //suspend fun getSeasonalAnime(year: String, season: String): List<AnimeData>
}