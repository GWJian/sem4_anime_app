package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.model.Data

interface AnimeRepo {

    suspend fun getTopAnimeList(): List<Data>

    suspend fun getDetailAnime(animeId: Int): Data?

    suspend fun searchAnime(query: String): List<Data>

    //suspend fun getSeasonNowAnime(): List<AnimeData>

    //suspend fun getSeasonalAnime(year: String, season: String): List<AnimeData>
}