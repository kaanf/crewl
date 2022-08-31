package com.example.crewl.presentation.bottomSheet.countryCode.providers

import com.example.crewl.data.models.Country

interface CountryListProvider {
    fun getCountries(): List<Country>
}
