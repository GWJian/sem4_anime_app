package com.gwj.sem4_anime_app.core.di

import com.gwj.sem4_anime_app.BuildConfig
import com.gwj.sem4_anime_app.core.util.Logger
import com.gwj.sem4_anime_app.data.api.AnimeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val logging = Logger()
    private val client = OkHttpClient().newBuilder().addInterceptor(logging).build()

    @Provides
    @Singleton
    fun provideAnimeApi(): AnimeApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AnimeApi::class.java)
    }
}