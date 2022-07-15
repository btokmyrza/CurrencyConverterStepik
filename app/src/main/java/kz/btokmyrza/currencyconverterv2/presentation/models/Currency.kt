package kz.btokmyrza.currencyconverterv2.presentation.models

data class Currency(
    val id: Int,
    val name: String,
    var amount: Int,
    val conversionRate: Float,
    val flagImageUrl: String
)
