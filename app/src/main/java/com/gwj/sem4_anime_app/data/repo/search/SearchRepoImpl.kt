package com.gwj.sem4_anime_app.data.repo.search

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.Data

class SearchRepoImpl(private val AnimeApi: AnimeApi) : SearchRepo {
    override suspend fun searchAnime(query: String): List<Data> {
        return AnimeApi.searchAnime(query).animeData ?: emptyList()
    }
}