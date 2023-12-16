package com.gwj.sem4_anime_app.data.repo.genres

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.DataX

class GenresRepoImpl(private val AnimeApi:AnimeApi): GenresRepo {

    override suspend fun getAllGenres(): List<DataX> {
        return AnimeApi.getAllGenres().genres
    }

}