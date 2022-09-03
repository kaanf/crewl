package com.example.crewl.domain.repository

import com.example.crewl.data.model.Country
import com.example.crewl.presentation.bottomSheet.countryCode.providers.AssetCountryProvider
import com.example.crewl.presentation.bottomSheet.countryCode.providers.CountryListProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryCodeRepository(private val provider: CountryListProvider) {
    private var cachedCountries: List<Country> = emptyList()

    suspend fun getCountries(isReload: Boolean = false) =
        withContext(Dispatchers.IO) {
            if (isReload || cachedCountries.isEmpty()) {
                val loadedCountries = provider.getCountries()
                cachedCountries = loadedCountries
            }
            cachedCountries
        }

    companion object {
        fun getFromAssets(countryFileName: String = "countries.txt"): CountryCodeRepository =
            CountryCodeRepository(provider = AssetCountryProvider(countryFileName))
    }
}


