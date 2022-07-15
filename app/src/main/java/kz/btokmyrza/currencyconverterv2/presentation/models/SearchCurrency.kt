package kz.btokmyrza.currencyconverterv2.presentation.models

import androidx.annotation.DrawableRes

data class SearchCurrency(
    val id: Int,
    val name: String,
    @DrawableRes val flagImage: Int
)