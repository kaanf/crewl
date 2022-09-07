package com.example.crewl.presentation.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crewl.domain.usecase.CheckUserUseCase
import com.example.crewl.helper.Status
import com.example.crewl.utils.ViewState
import com.example.crewl.utils.getViewStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MailViewModel @Inject constructor(private val checkUserUseCase: CheckUserUseCase) : ViewModel() {
    private var _isUserRegistered = MutableStateFlow<ViewState<Boolean>>(ViewState.Empty(true))
    val isUserRegistered: StateFlow<ViewState<Boolean>> = _isUserRegistered

    @ExperimentalCoroutinesApi
    fun checkMail(mail: String) {
        viewModelScope.launch {
            getViewStateFlow {
                checkUserUseCase.execute(mail = mail)
            }.collect {
                when (it) {
                    is ViewState.Empty -> _isUserRegistered.value = it
                    is ViewState.Loading -> _isUserRegistered.value = it
                    is ViewState.RenderFailure -> _isUserRegistered.value = it
                    is ViewState.RenderSuccess<Boolean> -> _isUserRegistered.value = it
                }
            }
        }
    }
}