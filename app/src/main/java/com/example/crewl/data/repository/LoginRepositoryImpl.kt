package com.example.crewl.data.repository

import com.example.crewl.domain.repository.LoginRepository
import com.example.crewl.helper.FirebaseUserActions
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val firebaseUserActions: FirebaseUserActions) : LoginRepository {
    override suspend fun signInWithEmailAndPassword(mail: String, password: String) {
        firebaseUserActions.signInWithEmailAndPassword(mail = mail, password = password)
    }
}