package com.gwj.sem4_anime_app.data.repo.search

import com.gwj.sem4_anime_app.data.model.Data

interface SearchRepo {
    suspend fun searchAnime(query: String): List<Data>
}