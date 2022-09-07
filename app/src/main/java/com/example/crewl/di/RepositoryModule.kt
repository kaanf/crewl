package com.example.crewl.di

import com.example.crewl.data.repository.SignInRepositoryImpl
import com.example.crewl.domain.repository.SignInRepository
import com.example.crewl.helper.FirebaseUserActions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideLoginRepository(firebaseUserActions: FirebaseUserActions): SignInRepository =
        SignInRepositoryImpl(firebaseUserActions = firebaseUserActions)
}