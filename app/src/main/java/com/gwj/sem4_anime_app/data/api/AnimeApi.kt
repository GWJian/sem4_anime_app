package com.gwj.sem4_anime_app.data.api

import com.gwj.sem4_anime_app.data.model.AnimeDetailResp
import com.gwj.sem4_anime_app.data.model.AnimeResp
import com.gwj.sem4_anime_app.data.model.GenresResp
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    //    Top Anime on Home Page:
    //    https://api.jikan.moe/v4/top/anime
    //    https://api.jikan.moe/v4/top/anime?limit=1
    //    https://api.jikan.moe/v4/top/anime?limit=25&page=1
    @GET("top/anime?limit=25&page=1")
    suspend fun getTopAnime(): AnimeResp

    //    Detail Anime:
    //    https://api.jikan.moe/v4/anime/35247/full <- get by id
    //    https://api.jikan.moe/v4/anime/35247
    @GET("anime/{id}")
    suspend fun getDetailAnime(@Path("id") animeId: Int): AnimeDetailResp

    //    Season Now anime:
    //    https://api.jikan.moe/v4/seasons/now?limit=25&page=1
    @GET("seasons/now")
    suspend fun getSeasonNowAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25,
        @Query("sfw") sfw: Boolean = true,
    ): AnimeResp

    //    Search anime name:
    //    https://api.jikan.moe/v4/anime?q=
    //    https://api.jikan.moe/v4/anime?q=naruto
    //    https://api.jikan.moe/v4/anime?q=type=tv&movie&page=1
    //    https://api.jikan.moe/v4/anime?genres=1,2&q=fullmetal&page=1
    @GET("anime")
    suspend fun searchAnime(
        @Query("genres") genres: String,
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("sfw") sfw: Boolean = true,
        @Query("limit") limit: Int = 25,
    ): AnimeResp


    //    Seasonal Anime List:
    //    https://api.jikan.moe/v4/seasons/{year}/{season}?limit=25
    //    https://api.jikan.moe/v4/seasons/2021/spring?limit=25&page=1
    @GET("seasons/{year}/{season}")
    suspend fun getSeasonalAnime(
        @Path("year") year: String,
        @Path("season") season: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25,
        @Query("sfw") sfw: Boolean = true,
    ): AnimeResp

    //    Random Anime Button:
    //    https://api.jikan.moe/v4/random/anime
    @GET("random/anime")
    suspend fun getRandomAnime(
        @Query("sfw") sfw: Boolean = true,
    ): AnimeDetailResp


    //    Get all genres:
    //    https://api.jikan.moe/v4/genres/anime
    @GET("genres/anime")
    suspend fun getAnimeGenres(): GenresResp

    // TODO get anime video,pv1,pv2,maybe no need to use this content page
    // https://api.jikan.moe/v4/anime/52991/videos

}