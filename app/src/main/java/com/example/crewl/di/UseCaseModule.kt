package com.example.crewl.di

import com.example.crewl.domain.repository.LoginRepository
import com.example.crewl.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase = LoginUseCase(repository = repository)
}