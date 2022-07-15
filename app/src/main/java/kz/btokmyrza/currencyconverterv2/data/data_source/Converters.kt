package kz.btokmyrza.currencyconverterv2.data.data_source

import androidx.room.TypeConverter
import kz.btokmyrza.currencyconverterv2.presentation.models.Currency
import kotlin.random.Random

class Converters {

    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return currency.flagImageUrl
    }

    @TypeConverter
    fun toCurrency(currencyFlagImageUrl: String): Currency {
        return Currency(
            id = Random.nextInt(),
            name = "",
            amount = 0,
            conversionRate = 0.0f,
            flagImageUrl = currencyFlagImageUrl
        )
    }

}