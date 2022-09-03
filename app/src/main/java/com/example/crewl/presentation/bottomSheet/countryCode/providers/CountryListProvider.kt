package com.example.crewl.presentation.bottomSheet.countryCode.providers

import com.example.crewl.data.model.Country

interface CountryListProvider {
    fun getCountries(): List<Country>
}
