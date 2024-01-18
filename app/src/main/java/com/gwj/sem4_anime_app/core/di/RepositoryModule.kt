package com.gwj.sem4_anime_app.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.gwj.sem4_anime_app.core.services.AuthService

import com.gwj.sem4_anime_app.data.api.AnimeApi
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepoImpl
import com.gwj.sem4_anime_app.data.repo.comment.CommentRepo
import com.gwj.sem4_anime_app.data.repo.comment.CommentRepoImpl

import com.gwj.sem4_anime_app.data.model.FavouriteAnime
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
    fun provideCommentRepo(authService: AuthService): CommentRepo
    {
        return CommentRepoImpl(authService = authService)
    }

//    @Provides
//    @Singleton
//    fun provideAnimeRepo(animeApi: AnimeApi): AnimeRepo {
//        return AnimeRepoImpl(animeApi)
//    }

    fun provideFavouriteRepo(authService: AuthService): FavouriteAnimeRepo {
        return FavouriteAnimeRepoImpl(authService)
    }


}