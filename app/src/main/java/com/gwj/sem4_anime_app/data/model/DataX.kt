package com.gwj.sem4_anime_app.data.model


//    Get all genres:
//    https://api.jikan.moe/v4/genres/anime
data class DataX(
    val count: Int,
    val mal_id: Int,
    val name: String,
    val url: String
)