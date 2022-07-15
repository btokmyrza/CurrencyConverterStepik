package kz.btokmyrza.currencyconverterv2.data.repository

import kz.btokmyrza.currencyconverterv2.data.remote.CurrencyRatesApi
import kz.btokmyrza.currencyconverterv2.domain.enitities.CurrencyResponse
import kz.btokmyrza.currencyconverterv2.domain.repository.CurrencyRatesRepository
import kz.btokmyrza.currencyconverterv2.util.Resource
import javax.inject.Inject

class CurrencyRatesRepositoryImpl @Inject constructor(
    private val api: CurrencyRatesApi
) : CurrencyRatesRepository {

    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error")
        }
    }

}