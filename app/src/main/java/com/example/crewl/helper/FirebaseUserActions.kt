package com.example.crewl.helper

interface FirebaseUserActions {
    /** Get current user id. **/
    fun currentUserId() : String?

    /** Method for sign in to Firebase. **/
    suspend fun signInWithEmailAndPassword(mail: String, password: String)

    /** Method for create user to Firebase. **/
    suspend fun createUserWithEmailAndPassword(mail: String, password: String)
}