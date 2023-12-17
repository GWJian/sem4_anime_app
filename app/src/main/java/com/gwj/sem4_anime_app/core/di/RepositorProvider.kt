package com.gwj.sem4_anime_app.core.di

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.repo.genres.GenresRepo
import com.gwj.sem4_anime_app.data.repo.genres.GenresRepoImpl
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepoImpl
import com.gwj.sem4_anime_app.data.repo.random.RandomAnimeRepo
import com.gwj.sem4_anime_app.data.repo.random.RandomAnimeRepoImpl
import com.gwj.sem4_anime_app.data.repo.search.SearchRepo
import com.gwj.sem4_anime_app.data.repo.search.SearchRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {

    @Provides
    @Singleton
    fun provideAnimeRepo(animeApi: AnimeApi): AnimeRepo {
        return AnimeRepoImpl(animeApi)
    }

    @Provides
    @Singleton
    fun provideGenresRepo(animeApi: AnimeApi): GenresRepo {
        return GenresRepoImpl(animeApi)
    }

    @Provides
    @Singleton
    fun provideSearchRepo(animeApi: AnimeApi): SearchRepo {
        return SearchRepoImpl(animeApi)
    }

    @Provides
    @Singleton
    fun provideRandomAnimeRepo(animeApi: AnimeApi): RandomAnimeRepo {
        return RandomAnimeRepoImpl(animeApi)
    }



}