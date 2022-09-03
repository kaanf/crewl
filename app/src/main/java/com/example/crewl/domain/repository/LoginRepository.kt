package com.example.crewl.domain.repository

interface LoginRepository {
    suspend fun signInWithEmailAndPassword(mail: String, password: String)
}