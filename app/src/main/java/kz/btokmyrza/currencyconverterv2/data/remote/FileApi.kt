package kz.btokmyrza.currencyconverterv2.data.remote

import kz.btokmyrza.currencyconverterv2.util.Constants.FLAG_BASE_URL
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface FileApi {

    @GET("small/tn_kz-flag.gif")
    suspend fun downloadFlagImage(): Response<ResponseBody>

    companion object {
        val instance: FileApi by lazy {
            Retrofit.Builder()
                .baseUrl(FLAG_BASE_URL)
                .build()
                .create(FileApi::class.java)
        }
    }

}