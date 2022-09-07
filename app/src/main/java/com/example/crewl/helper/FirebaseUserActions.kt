package com.example.crewl.helper

import com.example.crewl.utils.IOTaskResult
import kotlinx.coroutines.flow.Flow

interface FirebaseUserActions {
    /** Get current user id. **/
    fun currentUserId() : String?

    /** Method for sign in to Firebase. **/
    suspend fun signInWithEmailAndPassword(mail: String, password: String)

    /** Method for create user to Firebase. **/
    suspend fun createUserWithEmailAndPassword(mail: String, password: String)

    suspend fun isUserExists(mail: String): Flow<IOTaskResult<Boolean>>
}