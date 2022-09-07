package com.example.crewl.domain.repository

import com.example.crewl.helper.Status
import com.example.crewl.utils.IOTaskResult
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    suspend fun signInWithEmailAndPassword(mail: String, password: String)

    suspend fun isUserExists(mail: String): Flow<IOTaskResult<Boolean>>

    fun isUserLoggedIn(): Boolean
}