package com.example.crewl.presentation.fragment.phone

import android.os.Bundle
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.crewl.R
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentPhoneBinding
import com.example.crewl.domain.repository.CountryCodeRepository
import com.example.crewl.helper.ConstantHelper
import com.example.crewl.helper.CountryCodeHelper
import com.example.crewl.helper.ResourceHelper
import com.example.crewl.manager.NavigationManager.Companion.safeNavigate
import com.example.crewl.presentation.fragment.login.LoginFragmentDirections
import com.example.crewl.presentation.fragment.login.LoginFragmentHelper.Companion.font
import com.example.crewl.utils.ValidationUtils

class PhoneFragment : BaseFragment<PhoneViewModel, FragmentPhoneBinding>() {
    private lateinit var binding: FragmentPhoneBinding

    override fun getViewModel(): Class<PhoneViewModel> = PhoneViewModel::class.java

    override fun getViewBinding(): FragmentPhoneBinding = FragmentPhoneBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?, viewModel: PhoneViewModel, binding: FragmentPhoneBinding) {
        this@PhoneFragment.binding = binding

        val repository = CountryCodeRepository.getFromAssets()
        CountryCodeHelper.setCustomRepository(repository = repository)
    }

    override fun setUIAction() {
        var sizeOfPhoneNumber = 0

        var countryCode = ""
        var phoneNumber: String?

        binding.countryCodeButton.setOnClickListener {
            showCountryCodeDialog()
        }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableList<String>>("data")?.observe(viewLifecycleOwner) { data ->
            val builder = StringBuilder()
            builder.append("(+${data[0]}) ${data[1]}")

            countryCode = "+" + data[0]

            binding.countryCodeButton.text = buildSpannedString {
                /* (+ Phone code) Country name. */
                color(ResourceHelper.getColor(R.color.white)) {
                    font(ResourceHelper.getFont(R.font.inter_bold)) {
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

        binding.sendCodeButton.setOnClickListener {
            val isPhoneNumberValid = ValidationUtils.isPhoneNumberValid(count = sizeOfPhoneNumber)

            val resId = ResourceHelper.getDrawable(R.drawable.ic_tick)
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

                phoneNumber?.let { number ->
                    val directionId = LoginFragmentDirections.actionLoginFragmentToVerificationFragment(phoneNumber = number)
                    navController.safeNavigate(direction = directionId)
                }
            }
        }
    }

    private fun showCountryCodeDialog() {
        // Todo: Change directionId.
        val directionId = LoginFragmentDirections.actionLoginFragmentToCountryCodeFragment()
        navController.safeNavigate(direction = directionId)
    }

    override fun getViewModelFactory(): ViewModelProvider.Factory? {
        return null
    }

}