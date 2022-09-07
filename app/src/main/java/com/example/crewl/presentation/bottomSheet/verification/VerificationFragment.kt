package com.example.crewl.presentation.bottomSheet.verification

import android.os.Bundle
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crewl.R
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentVerificationBinding
import com.example.crewl.helper.ApplicationLog.setLog
import com.example.crewl.helper.ResourceHelper
import com.example.crewl.presentation.fragment.login.SignInFragmentDirections
import com.example.crewl.utils.font
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class VerificationFragment : BaseFragment<VerificationViewModel, FragmentVerificationBinding>() {
    private lateinit var binding: FragmentVerificationBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var phoneAuthOptions: PhoneAuthOptions
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationId: String = ""

    private val args: VerificationFragmentArgs by navArgs()

    private var phoneNumber: String? = null

    override fun getViewModel(): Class<VerificationViewModel> = VerificationViewModel::class.java

    override fun getViewBinding(): FragmentVerificationBinding = FragmentVerificationBinding.inflate(layoutInflater)

    override fun onCreate(
        savedInstanceState: Bundle?,
        viewModel: VerificationViewModel,
        binding: FragmentVerificationBinding
    ) {
        this@VerificationFragment.binding = binding

        auth = FirebaseAuth.getInstance()
        setPhoneAuthProviderCallback()

        phoneNumber = args.phoneNumber

        phoneNumber?.let { number ->
            setPhoneAuthOptions(number = number)
            PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
        }
    }

    override fun setUIAction() {
        binding.resendCodeText.text = buildSpannedString {
            /* Don't have an account */
            color(ResourceHelper.getColor(R.color.white)) {
                font(ResourceHelper.getFont(R.font.inter_regular)) {
                    append(getString(R.string.did_not_get_the_code))
                }
            }
            /* Get started */
            color(ResourceHelper.getColor(R.color.default_button_bg)) {
                font(ResourceHelper.getFont(R.font.inter_bold)) {
                    append(getString(R.string.resend))
                }
            }
        }

        binding.phoneNumberText.text = phoneNumber ?: ""
    }

    private fun setPhoneAuthProviderCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(otpId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@VerificationFragment.verificationId = otpId
                setLog("OTP is sent.")
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                logInWithPhoneCredentials(credential = credential)
                setLog("OTP is completed.")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                setLog("OTP is failed.")
            }
        }
    }

    private fun logInWithPhoneCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()
        ) { task ->
            if (task.isSuccessful) {
                val directionId: NavDirections = SignInFragmentDirections.actionSignInFragmentToVerificationFragment()
                findNavController().navigate(directionId)
            }
        }
    }

    private fun setPhoneAuthOptions(number: String) {
        phoneAuthOptions = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
    }
}