package com.gwj.sem4_anime_app.data.repo.search

import com.gwj.sem4_anime_app.data.model.AnimeData

interface SearchRepo {
    suspend fun searchAnime(query: String): List<AnimeData>

    suspend fun getAnimeByGenre(query: String): List<AnimeData>
}