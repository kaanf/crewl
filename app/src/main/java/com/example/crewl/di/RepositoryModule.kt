package com.example.crewl.di

import com.example.crewl.data.repository.LoginRepositoryImpl
import com.example.crewl.domain.repository.LoginRepository
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
    fun provideLoginRepository(firebaseUserActions: FirebaseUserActions): LoginRepository =
        LoginRepositoryImpl(firebaseUserActions = firebaseUserActions)
}