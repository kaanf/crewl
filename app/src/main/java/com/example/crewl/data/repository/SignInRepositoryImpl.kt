package com.example.crewl.data.repository

import com.example.crewl.domain.repository.SignInRepository
import com.example.crewl.helper.FirebaseUserActions
import com.example.crewl.utils.IOTaskResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(private val firebaseUserActions: FirebaseUserActions) : SignInRepository {
    override suspend fun signInWithEmailAndPassword(mail: String, password: String) {
        firebaseUserActions.signInWithEmailAndPassword(mail = mail, password = password)
    }

    override suspend fun isUserExists(mail: String): Flow<IOTaskResult<Boolean>> =
        firebaseUserActions.isUserExists(mail = mail)

    override fun isUserLoggedIn(): Boolean = firebaseUserActions.currentUserId() != null
}