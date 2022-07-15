package kz.btokmyrza.currencyconverterv2.domain.enitities

data class CurrencyResponse(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Rates,
)