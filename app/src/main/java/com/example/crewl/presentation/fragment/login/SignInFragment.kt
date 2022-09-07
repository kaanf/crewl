/**
 * @author Kaan Fırat
 *
 * Last updated time : 3 September 2022 05:34
 */

package com.example.crewl.presentation.fragment.login

import android.os.Bundle
import android.view.View
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crewl.R
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentSignInBinding
import com.example.crewl.helper.ResourceHelper.getColor
import com.example.crewl.helper.ResourceHelper.getFont
import com.example.crewl.helper.Status
import com.example.crewl.manager.NavigationManager
import com.example.crewl.manager.NavigationManager.deleteAfterNavigate
import com.example.crewl.manager.NavigationManager.navigateSafely
import com.example.crewl.utils.*
import com.example.crewl.utils.ValidationUtils.isMailValid
import com.example.crewl.utils.ValidationUtils.isPasswordValid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel, FragmentSignInBinding>() {
    private var binding: FragmentSignInBinding by autoCleared()
    private val viewModel: SignInViewModel by viewModels()

    override fun getViewModel(): Class<SignInViewModel> = SignInViewModel::class.java

    override fun getViewBinding(): FragmentSignInBinding = FragmentSignInBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?, viewModel: SignInViewModel, binding: FragmentSignInBinding) {
        this@SignInFragment.binding = binding

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.signInState.collect { status ->
                when (status) {
                    is Status.Success -> {
                        binding.loginButton.setLoading(isLoading = false)
                        setAlpha(isAlphaActive = true)
                    }
                    is Status.Error -> {
                        binding.loginButton.setLoading(isLoading = false)

                        setAlpha(isAlphaActive = true)
                    }
                    is Status.Loading -> {
                        binding.loginButton.setLoading(isLoading = true)
                        setAlpha(isAlphaActive = false)
                    }
                    is Status.Empty -> {
                        binding.loginButton.setLoading(isLoading = false)
                        setAlpha(isAlphaActive = true)
                    }
                }
            }
        }
    }

    /* Todo: Make custom error design for EditText and change EditText with TextInputLayout (maybe). */

    override fun setUIAction() {
        binding.loginButton.setOnClickListener {
            val password: String = binding.passwordEditText.text.asString

        }

        binding.textInputPassword.error = "Hey, This is a wrong password."
    }

    private fun setAlpha(isAlphaActive: Boolean) {
        if (!isAlphaActive) {
            binding.passwordEditText.withObjectAnimation(property = "alpha", value = 0.5f)
        } else {
            binding.passwordEditText.withObjectAnimation(property = "alpha", value = 1f)
        }
    }

    /*
    private fun setClickable(isClickable: Boolean) {
        binding.mailEditText.isClickable = isClickable
        binding.passwordEditText.isClickable = isClickable
    }

    private fun setFocusable(isFocusable: Boolean) {
        binding.mailEditText.isFocusable = isFocusable
        binding.passwordEditText.isFocusable = isFocusable
    }
    */
}
