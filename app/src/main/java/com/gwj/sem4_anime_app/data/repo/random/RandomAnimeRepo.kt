package com.gwj.sem4_anime_app.data.repo.random

import com.gwj.sem4_anime_app.data.model.Data

interface RandomAnimeRepo {

    suspend fun getRandomAnime(): Data

}