package com.gwj.sem4_anime_app.core.di

import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.repo.favourite.FavouriteAnimeRepo
import com.gwj.sem4_anime_app.data.repo.favourite.FavouriteAnimeRepoImpl
import com.gwj.sem4_anime_app.data.repo.user.UsersRepo
import com.gwj.sem4_anime_app.data.repo.user.UsersRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUsersRepo(authService: AuthService): UsersRepo
    {
        return UsersRepoImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideFavouriteRepo(authService: AuthService): FavouriteAnimeRepo {
        return FavouriteAnimeRepoImpl(authService)
    }

}