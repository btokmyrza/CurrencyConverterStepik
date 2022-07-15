package kz.btokmyrza.currencyconverterv2.presentation.fragments.personal_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.btokmyrza.currencyconverterv2.R

class PersonalPageViewModel : ViewModel() {

    private val _imageId = MutableLiveData<Int>().apply {
        value = R.drawable.harold_cropped
    }
    val imageId: LiveData<Int> = _imageId
}