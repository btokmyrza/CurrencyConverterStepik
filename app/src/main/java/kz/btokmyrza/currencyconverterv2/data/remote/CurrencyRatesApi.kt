package kz.btokmyrza.currencyconverterv2.data.remote

import kz.btokmyrza.currencyconverterv2.domain.enitities.CurrencyResponse
import kz.btokmyrza.currencyconverterv2.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyRatesApi {

    @Headers("apiKey: $API_KEY")
    @GET("latest")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>

}