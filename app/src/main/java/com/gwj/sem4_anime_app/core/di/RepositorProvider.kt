package com.gwj.sem4_anime_app.core.di

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepoImpl
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

}