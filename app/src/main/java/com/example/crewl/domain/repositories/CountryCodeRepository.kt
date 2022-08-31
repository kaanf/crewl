package com.example.crewl.domain.repositories

import android.util.Log
import com.example.crewl.data.models.Country
import com.example.crewl.ui.bottomSheet.countryCode.providers.AssetCountryProvider
import com.example.crewl.ui.bottomSheet.countryCode.providers.CountryListProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * CountryRepository handles country list loading from provider and caching as well as
 * country lookup based ISO code or device SIM settings (if available).
 *
 * @param provider - source of country information. Uses csv asset file by default.
 * @param defaultCountry - country that will be returned in case of lookup failure.
 */
class CountryCodeRepository(
    private val provider: CountryListProvider,
    private val defaultCountry: Country = Country.UNDEFINED
) {
    private var cachedCountriesList: List<Country> = emptyList()

    /**
     * Lazily loads country information from provider and returns whole list.
     * Values are cached on first call, use `forceReload` parameter to invalidate cache.
     */
    suspend fun countries(forceReload: Boolean = false) = withContext(Dispatchers.IO) {
        if (forceReload || cachedCountriesList.isEmpty()) {
            Log.i("CountryRepository", "Loading country list from provider")
            val loadedCountries = provider.getCountries()
            cachedCountriesList = loadedCountries
        }
        cachedCountriesList
    }

    private suspend fun findCountry(countries: List<Country>, code: String): Country? = withContext(
        Dispatchers.Default
    ) {
        countries.firstOrNull { code == it.isoCode }
    }

    companion object {
        /**
         * Create default repository with bundled file.
         *
         * Notes on custom file formatting:
         * Default file uses "phone_code;2_letter_iso;country_name" format (e.g. 371;LV;Latvia).
         * If custom filed does not adhere to this standard - custom `CountryListProvider` must be implemented
         *
         * @param context - application context to access assets
         * @param countryFileName - name of the country list file to use.
         * @param defaultCountry - country to be returned in case of lookup failure
         */
        fun fromAssets(
            countryFileName: String = "countries.txt",
            defaultCountry: Country = Country.UNDEFINED
        ): CountryCodeRepository {
            return CountryCodeRepository(
                AssetCountryProvider(countryFileName),
                defaultCountry
            )
        }
    }
}


