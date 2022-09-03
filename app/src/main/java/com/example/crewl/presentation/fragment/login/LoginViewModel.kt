package com.example.crewl.presentation.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crewl.domain.repository.LoginRepository
import com.example.crewl.domain.usecase.LoginUseCase
import com.example.crewl.helper.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/* Empty view model */
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private var _signInState = MutableStateFlow<Status<Boolean, String>>(Status.Empty())
    val signInState: StateFlow<Status<Boolean, String>> = _signInState

    fun signIn(mail: String, password: String) {
        loginUseCase.invoke(mail = mail, password = password).onEach { status ->
            when (status) {
                is Status.Success -> _signInState.value = Status.Success(status.data!!)
                is Status.Error -> _signInState.value = Status.Error(status.message)
                is Status.Loading -> _signInState.value = Status.Loading(data = null)
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}