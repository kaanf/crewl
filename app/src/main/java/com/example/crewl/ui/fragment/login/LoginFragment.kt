package com.example.crewl.ui.fragment.login

import android.os.Bundle
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.crewl.R
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentLoginBinding
import com.example.crewl.helpers.ApplicationLog.setLog
import com.example.crewl.helpers.ConstantHelper
import com.example.crewl.helpers.CountryCodeHelper
import com.example.crewl.helpers.ResourceHelper.getColor
import com.example.crewl.helpers.ResourceHelper.getDrawable
import com.example.crewl.helpers.ResourceHelper.getFont
import com.example.crewl.manager.NavigationManager.Companion.safeNavigate
import com.example.crewl.domain.repositories.CountryCodeRepository
import com.example.crewl.ui.fragment.login.LoginFragmentHelper.Companion.font
import com.example.crewl.utils.ValidationUtils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var phoneOptions: PhoneAuthOptions
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationId: String = ""

    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun onCreate(
        savedInstanceState: Bundle?,
        viewModel: LoginViewModel,
        binding: FragmentLoginBinding
    ) {
        this@LoginFragment.binding = binding

        auth = FirebaseAuth.getInstance()

        val repository = CountryCodeRepository.fromAssets()
        CountryCodeHelper.setCustomRepository(repository = repository)
    }

    override fun setUIAction() {
        var sizeOfPhoneNumber = 0

        var countryCode: String = ""
        var phoneNumber: String? = null

        setSpannableText()

        binding.countryCodeButton.setOnClickListener {
            showCountryCodeDialog()
        }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableList<String>>("data")?.observe(viewLifecycleOwner) { data ->
            val builder = StringBuilder()
            builder.append("(+${data[0]}) ${data[1]}")

            countryCode = "+" + data[0]

            binding.countryCodeButton.text = buildSpannedString {
                /* (+ Phone code) Country name. */
                color(getColor(R.color.white)) {
                    font(getFont(R.font.inter_bold)) {
                        append(builder.toString())
                    }
                }
            }
        }

        binding.phoneEditText.apply {
            doOnTextChanged { _, _, _, count ->
                sizeOfPhoneNumber = (count - 5)
            }

            setMask(ConstantHelper.phoneNumberMaskFilter)
        }

        binding.loginButton.setOnClickListener {
            val isPhoneNumberValid = ValidationUtils.checkPhoneNumber(count = sizeOfPhoneNumber)

            val resId = getDrawable(R.drawable.ic_tick)
            resId?.setBounds(0, 0, resId.intrinsicWidth, resId.intrinsicHeight)

            /* Todo: Create a custom error drawable. */
            if (!isPhoneNumberValid) {
                binding.phoneEditText.error = getString(R.string.phone_number_error)
                binding.phoneEditText.requestFocus()
            } else if (countryCode == "") {
                binding.countryCodeButton.error = getString(R.string.country_code_error)
                binding.countryCodeButton.requestFocus()
            } else {
                binding.countryCodeButton.error = null
                phoneNumber = countryCode + binding.phoneEditText.unMaskedText

                sendVerificationCode()
            }
        }
    }

    private fun logInWithPhoneCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()
        ) { task ->
            if (task.isSuccessful) {
                val directionId: NavDirections = LoginFragmentDirections.actionLoginFragmentToVerificationFragment()
                findNavController().navigate(directionId)
            }
        }
    }

    private fun sendVerificationCode() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(otpId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@LoginFragment.verificationId = otpId
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                logInWithPhoneCredentials(credential = credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                setLog("OTP is failed.")
            }
        }
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

    private fun showCountryCodeDialog() {
        navController.safeNavigate(LoginFragmentDirections.actionLoginFragmentToCountryCodeFragment())
    }

    private fun setPhoneAuthOptions(number: String) {
        PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
    }
}
