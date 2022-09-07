package com.example.crewl.domain.usecase

import android.util.Log
import com.example.crewl.domain.repository.SignInRepository
import com.example.crewl.helper.Status
import com.example.crewl.utils.IOTaskResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CheckUserUseCase @Inject constructor(private val repository: SignInRepository) {
    suspend fun execute(mail: String): Flow<IOTaskResult<Boolean>> = repository.isUserExists(mail = mail)
}