package kz.btokmyrza.currencyconverterv2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class MainViewModel: ViewModel() {

    private val _chosenIndex = MutableLiveData<Int>()
    val chosenIndex: LiveData<Int> = _chosenIndex

}