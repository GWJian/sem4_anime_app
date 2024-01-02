package com.gwj.sem4_anime_app.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.core.services.AuthServiceImpl
import com.gwj.sem4_anime_app.core.services.StorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideAuthenticationService(): AuthService {
        return AuthServiceImpl()
    }

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore
    {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun provideStorageService(): StorageService
    {
        return StorageService()
    }


}