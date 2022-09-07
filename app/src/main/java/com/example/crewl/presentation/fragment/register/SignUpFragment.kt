package com.example.crewl.presentation.fragment.register

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentSignUpBinding
import com.example.crewl.utils.autoCleared

class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignUpBinding>() {
    private var binding: FragmentSignUpBinding by autoCleared()
    private val viewModel: SignUpViewModel by viewModels()

    override fun getViewModel(): Class<SignUpViewModel> = SignUpViewModel::class.java

    override fun getViewBinding(): FragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?, viewModel: SignUpViewModel, binding: FragmentSignUpBinding) {
        this@SignUpFragment.binding = binding
    }

    override fun setUIAction() {
        TODO("Not yet implemented")
    }


}