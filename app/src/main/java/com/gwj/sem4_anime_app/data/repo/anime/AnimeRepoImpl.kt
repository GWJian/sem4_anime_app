package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.AnimeData
import com.gwj.sem4_anime_app.data.model.Data

class AnimeRepoImpl(private val animeApi: AnimeApi) : AnimeRepo {

    //use AnimeData cuz need pagination to handle the pagination of the data
    override suspend fun getTopAnimeList(): List<AnimeData> {
        return animeApi.getTopAnime().animeData ?: emptyList()
    }

    override suspend fun getSeasonalAnime(year: String, season: String): List<AnimeData> {
        return animeApi.getSeasonalAnime(year, season).animeData ?: emptyList()
    }

    //use Data cuz no pagination only 1 anime
    override suspend fun getDetailAnime(id: Int): Data {
        return animeApi.getDetailAnime(id)
    }


}