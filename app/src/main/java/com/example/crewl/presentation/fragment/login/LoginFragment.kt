package com.example.crewl.presentation.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.crewl.R
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentLoginBinding
import com.example.crewl.helper.ResourceHelper.getColor
import com.example.crewl.helper.ResourceHelper.getFont
import com.example.crewl.helper.Status
import com.example.crewl.presentation.fragment.login.LoginFragmentHelper.Companion.font
import com.example.crewl.utils.ValidationUtils.isMailValid
import com.example.crewl.utils.ValidationUtils.isPasswordValid
import com.example.crewl.utils.asString
import com.example.crewl.utils.autoCleared
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    private var binding: FragmentLoginBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModels()

    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory? = null

    override fun getViewBinding(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    override fun onCreate(
        savedInstanceState: Bundle?,
        viewModel: LoginViewModel,
        binding: FragmentLoginBinding
    ) {
        this@LoginFragment.binding = binding

        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        val isUserLoggedIn = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.signInState.collect { status ->
                when (status) {
                    is Status.Success -> {
                        binding.loginButton.setLoading(isLoading = false)
                        Snackbar.make(requireView(), "Success", Snackbar.LENGTH_SHORT).show()

                        setClickable(isClickable = true)
                        setFocusable(isFocusable = true)

                        Log.i("App.tag", "launchWhenStarted Success: called.")
                    }
                    is Status.Error -> {
                        binding.loginButton.setLoading(isLoading = false)
                        Snackbar.make(requireView(), "Error", Snackbar.LENGTH_SHORT).show()

                        setClickable(isClickable = true)
                        setFocusable(isFocusable = true)

                        Log.i("App.tag", "launchWhenStarted Error: called.")
                    }
                    else -> {
                        binding.loginButton.setLoading(isLoading = true)
                        Snackbar.make(requireView(), "Loading", Snackbar.LENGTH_SHORT).show()

                        setClickable(isClickable = false)
                        setFocusable(isFocusable = false)

                        Log.i("App.tag", "launchWhenStarted Loading: called.")
                    }
                }
            }
        }
    }

    override fun setUIAction() {
        setSpannableText()

        binding.loginButton.setOnClickListener {
            val mail: String = binding.mailEditText.text.asString
            val password: String = binding.passwordEditText.text.asString
            binding.loginButton.setLoading(true)

            val isMailValid = isMailValid(mail = mail)
            val isPasswordValid = isPasswordValid(password = password)

            if (isMailValid && isPasswordValid) {
                viewModel.signIn(mail = mail, password = password)
            } else {
                if (!isMailValid) {
                    binding.mailEditText.error = "Error, please try again mail."
                    binding.mailEditText.requestFocus()
                } else {
                    binding.passwordEditText.error = "Error, please try again password."
                    binding.passwordEditText.requestFocus()
                }
            }
        }
    }

    private fun setClickable(isClickable: Boolean) {
        binding.mailEditText.isClickable = isClickable
        binding.passwordEditText.isClickable = isClickable
    }

    private fun setFocusable(isFocusable: Boolean) {
        binding.mailEditText.isFocusable = isFocusable
        binding.passwordEditText.isFocusable = isFocusable
    }

    private fun setSpannableText() {
        binding.registerText.text = buildSpannedString {
            /* Don't have an account */
            color(getColor(R.color.white)) {
                font(getFont(R.font.inter_regular)) {
                    append(getString(R.string.dont_have_an_account))
                }
            }
            /* Get started */
            color(getColor(R.color.default_button_bg)) {
                font(getFont(R.font.inter_bold)) {
                    append(getString(R.string.get_started))
                }
            }
        }

        binding.termsOfServiceText.text = buildSpannedString {
            color(getColor(R.color.subtitle_gray_color)) {
                font(getFont(R.font.inter_medium)) {
                    append(getString(R.string.terms_of_policy_p1))
                }
            }
            color(getColor(R.color.white)) {
                font(getFont(R.font.inter_semibold)) {
                    append(getString(R.string.terms_of_policy))
                }
            }
            color(getColor(R.color.subtitle_gray_color)) {
                font(getFont(R.font.inter_medium)) {
                    append(getString(R.string.terms_of_policy_p2))
                }
            }
            color(getColor(R.color.white)) {
                font(getFont(R.font.inter_semibold)) {
                    append(getString(R.string.privacy_policy))
                }
            }
        }
    }
}
