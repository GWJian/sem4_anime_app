package com.gwj.sem4_anime_app.data.model

import com.gwj.sem4_anime_app.data.model.data.Aired
import com.gwj.sem4_anime_app.data.model.data.Broadcast
import com.gwj.sem4_anime_app.data.model.data.Demographic
import com.gwj.sem4_anime_app.data.model.data.Genre
import com.gwj.sem4_anime_app.data.model.data.Images
import com.gwj.sem4_anime_app.data.model.data.Producer
import com.gwj.sem4_anime_app.data.model.data.Studio
import com.gwj.sem4_anime_app.data.model.data.Title
import com.gwj.sem4_anime_app.data.model.data.Trailer

//    Top Anime on Home Page:
//    https://api.jikan.moe/v4/top/anime?limit=1
//    https://api.jikan.moe/v4/anime/35247
data class Data(
    val aired: Aired,
    val airing: Boolean,
    val approved: Boolean,
    val background: String,
    val broadcast: Broadcast,
    val demographics: List<Demographic>,
    val duration: String,
    val episodes: Int,
    val explicit_genres: List<Any>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val licensors: List<Any>,
    val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val producers: List<Producer>,
    val rank: Int,
    val rating: String,
    val score: Double,
    val scored_by: Int,
    val season: String,
    val source: String,
    val status: String,
    val studios: List<Studio>,
    val synopsis: String,
    val themes: List<Any>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val titles: List<Title>,
    val trailer: Trailer,
    val type: String,
    val url: String,
    val year: Int
)