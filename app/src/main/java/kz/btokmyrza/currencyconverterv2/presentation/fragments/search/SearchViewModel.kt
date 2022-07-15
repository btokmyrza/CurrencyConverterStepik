package kz.btokmyrza.currencyconverterv2.presentation.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.btokmyrza.currencyconverterv2.presentation.models.SearchCurrency
import kz.btokmyrza.currencyconverterv2.util.Constants.INITIAL_SEARCH_CURRENCY_LIST

class SearchViewModel : ViewModel() {

    private val _searchCurrencies = MutableLiveData<List<SearchCurrency>>().apply {
        value = INITIAL_SEARCH_CURRENCY_LIST
    }
    val searchCurrencies: LiveData<List<SearchCurrency>> = _searchCurrencies

}