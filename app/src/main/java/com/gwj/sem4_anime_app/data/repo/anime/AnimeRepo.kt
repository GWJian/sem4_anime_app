package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.model.AnimeData
import com.gwj.sem4_anime_app.data.model.Data

interface AnimeRepo {

    suspend fun getTopAnimeList(): List<AnimeData>

    suspend fun getSeasonalAnime(): List<AnimeData>

    suspend fun getDetailAnime(id: Int): Data


}