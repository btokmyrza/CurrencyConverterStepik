package kz.btokmyrza.currencyconverterv2.domain.repository

import kz.btokmyrza.currencyconverterv2.domain.enitities.CurrencyResponse
import kz.btokmyrza.currencyconverterv2.util.Resource

interface CurrencyRatesRepository {

    suspend fun getRates(
        base: String
    ): Resource<CurrencyResponse>

}