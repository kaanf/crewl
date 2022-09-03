package com.example.crewl.domain.usecase

import com.example.crewl.domain.repository.LoginRepository
import com.example.crewl.helper.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import java.lang.Exception
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
    operator fun invoke(mail: String, password: String): Flow<Status<Boolean, String>> = flow {
        emit(Status.Loading(data = null))

        try {
            repository.signInWithEmailAndPassword(mail = mail, password = password)
            emit(Status.Success(data = true))
        } catch (e: Exception) {
            emit(Status.Error(message = e.message, data = false))
        }
    }
}