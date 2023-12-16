package com.gwj.sem4_anime_app.data.repo.anime

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.AnimeData
import com.gwj.sem4_anime_app.data.model.Data

class AnimeRepoImpl(private val AnimeApi: AnimeApi) : AnimeRepo {

    //AnimeData cuz need pagination
    override suspend fun getTopAnimeList(): List<AnimeData> {
        return AnimeApi.getTopAnime().animeData ?: emptyList()
    }

    override suspend fun getSeasonalAnime(year: String, season: String): List<AnimeData> {
        return AnimeApi.getSeasonalAnime(year, season).animeData ?: emptyList()
    }

    //Data cuz no pagination only 1 anime
    override suspend fun getDetailAnime(id: Int): Data {
        return AnimeApi.getDetailAnime(id)
    }


}