package com.example.crewl.helper

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthentication @Inject constructor(private val auth: FirebaseAuth): FirebaseUserActions {
    override fun currentUserId(): String? = auth.currentUser?.uid

    override suspend fun signInWithEmailAndPassword(mail: String, password: String) {
        auth.signInWithEmailAndPassword(mail, password).await()
    }

    override suspend fun createUserWithEmailAndPassword(mail: String, password: String) {
        TODO("Not yet implemented")
    }
}