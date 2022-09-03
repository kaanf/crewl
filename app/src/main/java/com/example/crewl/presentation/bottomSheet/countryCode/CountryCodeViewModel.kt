package com.example.crewl.presentation.bottomSheet.countryCode

import androidx.lifecycle.*
import com.example.crewl.data.model.Country
import com.example.crewl.domain.repository.CountryCodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryCodeViewModel(private val repository: CountryCodeRepository): ViewModel() {
    private val countries = MutableLiveData<List<Country>>().apply { postValue(emptyList()) }
    private var cachedAllCountries = emptyList<Country>()

    fun loadCountries() {
        viewModelScope.launch {
            cachedAllCountries = repository.getCountries()
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