package com.example.crewl.presentation.fragment.login

import androidx.lifecycle.ViewModel
import com.example.crewl.helpers.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/* Empty view model */
class LoginViewModel: ViewModel() {
    private var _signInState= MutableStateFlow<Status<Boolean,String>>(Status.Empty())
    val signInState: StateFlow<Status<Boolean, String>> = _signInState

}