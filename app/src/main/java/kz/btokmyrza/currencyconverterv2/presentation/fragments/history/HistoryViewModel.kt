package kz.btokmyrza.currencyconverterv2.presentation.fragments.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.btokmyrza.currencyconverterv2.domain.enitities.Transaction
import kz.btokmyrza.currencyconverterv2.domain.repository.TransactionsRepository
import kz.btokmyrza.currencyconverterv2.util.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _transactions = MutableLiveData<List<Transaction>>().apply {
        value = emptyList()
    }
    val transactions: LiveData<List<Transaction>> = _transactions

    fun loadAllTransactions() {
        viewModelScope.launch(dispatchers.io) {
            _transactions.postValue(transactionsRepository.getAllTransactions())
        }
    }

    fun deleteTransaction(transaction: Transaction): Int {
        viewModelScope.launch(dispatchers.io) {
            transactionsRepository.deleteTransaction(transaction)
        }
        val currentList = _transactions.value?.toMutableList()
        val id = transaction.id
        val removedIndex = currentList?.indexOfFirst { it.id == id }
        currentList?.removeAt(removedIndex!!)
        _transactions.value = currentList

        return removedIndex!!
    }

    fun returnTransaction(transaction: Transaction, position: Int) {
        viewModelScope.launch(dispatchers.io) {
            transactionsRepository.upsert(transaction)
        }
        val currentList = _transactions.value?.toMutableList()
        currentList?.add(position, transaction)
        _transactions.value = currentList
    }

}