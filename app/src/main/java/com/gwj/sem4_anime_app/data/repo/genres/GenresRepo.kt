package com.gwj.sem4_anime_app.data.repo.genres

import com.gwj.sem4_anime_app.data.model.DataX

interface GenresRepo {
    suspend fun getAllGenres(): List<DataX>
}