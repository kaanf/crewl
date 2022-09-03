@file:Suppress("UNCHECKED_CAST")

package com.example.crewl.presentation.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crewl.domain.usecase.LoginUseCase

class LoginViewModelFactory(private val loginUseCase: LoginUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModelFactory(loginUseCase) as T
    }
}