package com.gwj.sem4_anime_app.data.repo.random

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.model.Data

class RandomAnimeRepoImpl(private val AnimeApi: AnimeApi) : RandomAnimeRepo {
    override suspend fun getRandomAnime(): Data {
        return AnimeApi.getRandomAnime()
    }
}