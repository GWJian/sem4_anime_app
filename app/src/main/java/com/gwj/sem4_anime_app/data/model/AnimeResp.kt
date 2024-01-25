package com.gwj.sem4_anime_app.data.model

import com.gwj.sem4_anime_app.data.model.data.Pagination

//    Top Anime on Home Page:
//    https://api.jikan.moe/v4/top/anime

data class AnimeResp(
    val data: List<Data>?,
    val pagination: Pagination?
)
