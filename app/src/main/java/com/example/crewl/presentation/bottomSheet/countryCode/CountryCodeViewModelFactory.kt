package com.example.crewl.presentation.bottomSheet.countryCode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crewl.domain.repository.CountryCodeRepository

class CountryCodeViewModelFactory {
    class Factory(private val repository: CountryCodeRepository) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CountryCodeViewModel(repository) as (T)
        }
    }
}