package com.example.crewl.helper

import androidx.fragment.app.FragmentManager
import com.example.crewl.presentation.bottomSheet.countryCode.CountryCodeFragment
import com.example.crewl.domain.repository.CountryCodeRepository

class CountryCodeHelper {
    companion object {
        fun setCustomRepository(repository: CountryCodeRepository) {
            customRepository = repository
        }

        private var customRepository: CountryCodeRepository? = null

        internal fun getCountryRepository() = customRepository ?: CountryCodeRepository.getFromAssets()

        fun showDialog(fragmentManager: FragmentManager) {
            CountryCodeFragment().show(fragmentManager, CountryCodeFragment.TAG)
        }
    }
}