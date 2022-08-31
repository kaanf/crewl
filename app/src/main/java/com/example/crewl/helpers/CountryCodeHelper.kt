package com.example.crewl.helpers

import androidx.fragment.app.FragmentManager
import com.example.crewl.ui.bottomSheet.countryCode.CountryCodeFragment
import com.example.crewl.domain.repositories.CountryCodeRepository

class CountryCodeHelper {
    companion object {
        fun setCustomRepository(repository: CountryCodeRepository) {
            customRepository = repository
        }

        private var customRepository: CountryCodeRepository? = null

        internal fun getCountryRepository() = customRepository ?: CountryCodeRepository.fromAssets()

        fun showDialog(fragmentManager: FragmentManager) {
            CountryCodeFragment().show(fragmentManager, CountryCodeFragment.TAG)
        }
    }
}