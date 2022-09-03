package com.example.crewl.di

import com.example.crewl.helper.FirebaseAuthentication
import com.example.crewl.helper.FirebaseUserActions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseUserActions(auth: FirebaseAuth): FirebaseUserActions = FirebaseAuthentication(auth = auth)

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}