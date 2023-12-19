package com.gwj.sem4_anime_app.data.api

import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.model.GenresResp
import com.gwj.sem4_anime_app.data.model.AnimeResp
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    //    Top Anime on Home Page:
    //    https://api.jikan.moe/v4/top/anime
    //    https://api.jikan.moe/v4/top/anime?limit=1
    //    https://api.jikan.moe/v4/top/anime?limit=25&page=1
    @GET("top/anime?limit=25&page=1")
    suspend fun getTopAnime(): AnimeResp //TODO: ASK SIR about this,是使用Path还是怎样,因为要用load more

    //    Seasonal Anime List:
    //    https://api.jikan.moe/v4/seasons/{year}/{season}?limit=25
    //    https://api.jikan.moe/v4/seasons/2021/spring?limit=25&page=1
    @GET("seasons/{year}/{season}")
    suspend fun getSeasonalAnime(
        @Path("year") year: String,
        @Path("season") season: String
    ): AnimeResp

    //    Detail Anime:
    //    https://api.jikan.moe/v4/anime/35247/full <- get by id
    //    https://api.jikan.moe/v4/anime/35247
    @GET("anime/{id}")
    suspend fun getDetailAnime(@Path("id") id: Int): Data

    //    Random Anime Button:
    //    https://api.jikan.moe/v4/random/anime
    @GET("random/anime")
    suspend fun getRandomAnime(): Data

    //    Get all genres:
    //    https://api.jikan.moe/v4/genres/anime
    @GET("genres/anime")
    suspend fun getAllGenres(): GenresResp

    //    Search anime name:
    //    https://api.jikan.moe/v4/anime?q=
    //    https://api.jikan.moe/v4/anime?q=naruto&limit=25&page=1
    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): AnimeResp

    //    Get anime by genre
    //    https://api.jikan.moe/v4/anime?genres=1,2
    @GET("anime")
    suspend fun getAnimeByGenre(@Query("genres") genres: String): AnimeResp

    // get anime video,pv1,pv2,maybe no need to use this
    // https://api.jikan.moe/v4/anime/52991/videos

}