package kz.btokmyrza.currencyconverterv2.presentation.models

data class FlagCurrency(
    val id: Int,
    val name: String,
    val countryCode: String,
    val flagImageUrl: String
)