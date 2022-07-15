package kz.btokmyrza.currencyconverterv2.presentation.fragments.convertor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.btokmyrza.currencyconverterv2.domain.enitities.Transaction
import kz.btokmyrza.currencyconverterv2.presentation.models.Currency
import kz.btokmyrza.currencyconverterv2.domain.repository.CurrencyRatesRepository
import kz.btokmyrza.currencyconverterv2.domain.repository.TransactionsRepository
import kz.btokmyrza.currencyconverterv2.util.DispatcherProvider
import kz.btokmyrza.currencyconverterv2.util.Resource
import javax.inject.Inject

const val TAG = "ConvertorViewModel"

@HiltViewModel
class ConvertorViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val currencyRatesRepository: CurrencyRatesRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _convertorCurrencies = MutableLiveData<List<Currency>>().apply {
        value = emptyList()
    }
    val convertorCurrencies: LiveData<List<Currency>> = _convertorCurrencies

    private val _currencyRate = MutableLiveData<String>()
    val currencyRate: LiveData<String> = _currencyRate

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> = _progressBarVisibility

    fun appendCurrency(currency: Currency) {
        val currentList = _convertorCurrencies.value?.toMutableList()
        currentList?.add(currency)
        _convertorCurrencies.value = currentList
    }

    fun getSelectedCurrencyRateInTenge(currencyCode: String) {
        _progressBarVisibility.value = true
        viewModelScope.launch(dispatchers.io) {
            when (val currencyRateResponse = currencyRatesRepository.getRates(currencyCode)) {
                is Resource.Error -> Log.e(TAG, currencyRateResponse.message!!)
                is Resource.Success -> _currencyRate.postValue(currencyRateResponse.data!!.rates.KZT.toString())
            }
            _progressBarVisibility.postValue(false)
        }
    }

    fun saveTransactionToDatabase(tengeAmount: String) {
        if (convertorCurrencies.value?.isEmpty() == true)
            return
        viewModelScope.launch(dispatchers.io) {
            transactionsRepository.upsert(
                Transaction(amount = tengeAmount.toInt(), currency = convertorCurrencies.value?.get(0))
            )
        }
    }

}