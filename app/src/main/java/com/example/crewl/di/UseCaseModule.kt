package com.example.crewl.di

import com.example.crewl.domain.repository.SignInRepository
import com.example.crewl.domain.usecase.CheckUserUseCase
import com.example.crewl.domain.usecase.SignInUseCase
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
    fun provideLoginUseCase(repository: SignInRepository): SignInUseCase = SignInUseCase(repository = repository)

    @Singleton
    @Provides
    fun provideCheckUserUseCase(repository: SignInRepository): CheckUserUseCase = CheckUserUseCase(repository = repository)
}