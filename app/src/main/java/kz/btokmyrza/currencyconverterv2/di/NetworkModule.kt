package kz.btokmyrza.currencyconverterv2.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.data.remote.CurrencyRatesApi
import kz.btokmyrza.currencyconverterv2.data.repository.CurrencyRatesRepositoryImpl
import kz.btokmyrza.currencyconverterv2.domain.repository.CurrencyRatesRepository
import kz.btokmyrza.currencyconverterv2.util.Constants.CURRENCY_API_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCurrencyRatesApi(
        @ApplicationContext context: Context
    ): CurrencyRatesApi {
        val client = OkHttpClient.Builder().addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        ).build()
        return Retrofit.Builder()
            .baseUrl(CURRENCY_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyRatesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRatesRepository(api: CurrencyRatesApi): CurrencyRatesRepository {
        return CurrencyRatesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

}