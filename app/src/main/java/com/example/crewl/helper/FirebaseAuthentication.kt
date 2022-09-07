package com.example.crewl.helper

import android.R.attr
import android.util.Log
import com.example.crewl.utils.AuthResult
import com.example.crewl.utils.IOTaskResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject

class FirebaseAuthentication @Inject constructor(private val auth: FirebaseAuth) : FirebaseUserActions {
    override fun currentUserId(): String? = auth.currentUser?.uid

    override suspend fun signInWithEmailAndPassword(mail: String, password: String) {
        auth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    try {
                        throw task.exception!!
                    } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                        Log.d("App.tag", "onComplete: invalid_email")
                    }
                }
            }.await()
    }

    override suspend fun createUserWithEmailAndPassword(mail: String, password: String) {

    }

    override suspend fun isUserExists(mail: String): Flow<IOTaskResult<Boolean>> {
        return flow {
            val task = auth.fetchSignInMethodsForEmail(mail).await()

            delay(1500)
            task.signInMethods?.let {
                if (it.size != 0) {
                    emit(IOTaskResult.OnSuccess(true))
                } else {
                    emit(IOTaskResult.OnFailed(IOException("No user exists.")))
                }
            } ?: emit(IOTaskResult.OnFailed(IOException("Fetch failed.")))
            return@flow
        }.catch {
            emit(IOTaskResult.OnFailed(IOException("Process failed.")))
            return@catch
        }.flowOn(Dispatchers.IO)
    }

    /*
    override suspend fun isUserExists(mail: String): Status<Boolean, String> {
        return try {
                val task = auth.fetchSignInMethodsForEmail(mail)

            if (task.isSuccessful) {
                task.result.signInMethods?.let {
                    if (it.size != 0) {
                        Status.Success(true)
                    }
                    else Status.Error("No user.", false)
                } ?: Status.Error("Fetch failed.", false)
            } else {
                Status.Error("Fetch failed.", false)
            }
        } catch (e: Exception) {
            Status.Error("Fetch 2 failed.", false)
        }
    }
     */


}