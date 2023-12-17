package com.gwj.sem4_anime_app.data.repo.search

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.AnimeData
import com.gwj.sem4_anime_app.data.model.AnimeResp
import com.gwj.sem4_anime_app.data.model.Data

class SearchRepoImpl(private val animeApi: AnimeApi) : SearchRepo {
    override suspend fun searchAnime(query: String): List<AnimeData> {
        // search anime with query and return list of anime
        return animeApi.searchAnime(query).animeData ?: emptyList()

    }
}
