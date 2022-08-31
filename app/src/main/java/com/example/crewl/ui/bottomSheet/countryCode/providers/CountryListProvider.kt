package com.example.crewl.ui.bottomSheet.countryCode.providers

import com.example.crewl.data.models.Country

interface CountryListProvider {
    fun getCountries(): List<Country>
}
