package com.example.crewl.domain.usecase

import com.example.crewl.domain.repository.SignInRepository
import com.example.crewl.helper.Status
import com.example.crewl.utils.IOTaskResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import java.lang.Exception
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: SignInRepository) {
    operator fun invoke(mail: String, password: String): Flow<Status<Boolean, String>> = flow {
        emit(Status.Loading(data = null))
        repository.signInWithEmailAndPassword(mail = mail, password = password)
        emit(Status.Success(data = true))
    }.catch {
        emit(Status.Error(null, false))
    }.flowOn(Dispatchers.IO)


    fun isUserLoggedIn(): Boolean = repository.isUserLoggedIn()
}
