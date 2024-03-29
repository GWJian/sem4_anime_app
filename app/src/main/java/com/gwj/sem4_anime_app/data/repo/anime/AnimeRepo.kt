package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.model.DataX

interface AnimeRepo {

    suspend fun getTopAnimeList(): List<Data>

    suspend fun getSeasonNowAnime(page: Int = 1): List<Data>

    suspend fun getDetailAnime(animeId: Int): Data?

    suspend fun searchAnime(genres: String, query: String, page: Int = 1): List<Data>

    suspend fun getRandomAnime(): Data?

    suspend fun getSeasonalAnime(year: String, season: String, page: Int = 1): List<Data>

    suspend fun getAnimeGenres(): List<DataX>
}