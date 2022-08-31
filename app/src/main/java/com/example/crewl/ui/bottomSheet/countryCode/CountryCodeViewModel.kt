package com.example.crewl.ui.bottomSheet.countryCode

import androidx.lifecycle.*
import com.example.crewl.data.models.Country
import com.example.crewl.domain.repositories.CountryCodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryCodeViewModel(private val repository: CountryCodeRepository): ViewModel() {
    private val countries = MutableLiveData<List<Country>>().apply { postValue(emptyList()) }
    private var cachedAllCountries = emptyList<Country>()

    fun loadCountries() {
        viewModelScope.launch {
            cachedAllCountries = repository.countries()
            countries.postValue(cachedAllCountries)
        }
    }

    fun getCountries(): LiveData<List<Country>> {
        return countries
    }

    fun setQuery(text: String?) {
        if (text == null) {
            countries.postValue(cachedAllCountries)
        } else {
            viewModelScope.launch(Dispatchers.Default) {
                val filtered = cachedAllCountries.filter { it.combinedName.contains(text, true) }
                countries.postValue(filtered)
            }
        }
    }
}