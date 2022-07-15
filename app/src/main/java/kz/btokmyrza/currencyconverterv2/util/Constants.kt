package kz.btokmyrza.currencyconverterv2.util

import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.presentation.models.*

object Constants {

    const val FIREBASE_REALTIME_DATABASE_URL = "https://currency-converter-stepik-default-rtdb.europe-west1.firebasedatabase.app"
    const val FIREBASE_REALTIME_DATABASE_MESSAGES_PATH = "messages"
    const val FIREBASE_BASE_URL = "https://fcm.googleapis.com/"
    const val SERVER_KEY = ""

    const val FLAG_BASE_URL = "https://www.worldometers.info//img/flags/"
    const val FLAG_KZ_URL = "https://www.worldometers.info//img/flags/small/tn_kz-flag.gif"

    const val CURRENCY_API_BASE_URL = "https://api.apilayer.com/exchangerates_data/"
    const val API_KEY = "vqdJOfzDgEpn2wKkDamUnaIudYEEYabC"

    const val COUNTRY_FLAGS_BASE_URL = "https://countryflagsapi.com/png/"
    val USED_COUNTRY_CODES = mapOf(
        "TUR" to "Lira, Turkey",
        "EUR" to "Euro, European Union",
        "USA" to "Dollar, United States of America",
        "GBR" to "Pound, Great Britain",
        "RUS" to "Ruble, Russian Federation",
        "JPN" to "Yen, Japan",
        "CHE" to "Frank, Switzerland",
        "SGP" to "Dollar, Singapore",
    )

    val INITIAL_SEARCH_CURRENCY_LIST = listOf(
        SearchCurrency(
            id = 0,
            name = "Доллары, США",
            flagImage = R.drawable.flag_usa
        ),
        SearchCurrency(
            id = 1,
            name = "Лира, Турция",
            flagImage = R.drawable.flag_tr
        ),
        SearchCurrency(
            id = 2,
            name = "Евро, EC",
            flagImage = R.drawable.flag_eu
        ),
        SearchCurrency(
            id = 3,
            name = "Тенге, Казахстан",
            flagImage = R.drawable.flag_kz
        )
    )

    /*val INITIAL_MESSAGES_LIST = listOf<ChatItem>(
        ChatSender(
            dateSent = "11.04.2020, 4:22:57.401",
            author = "Azat",
            message = "Cәлем"
        ),
        ChatDate("11.04.2020"),
        ChatReceiver(
            dateSent = "11.04.2020, 4:21:57.401",
            author = "Batyrkhan",
            message = "ti tut?"
        ),
        ChatReceiver(
            dateSent = "11.04.2020, 4:20:57.401",
            author = "Batyrkhan",
            message = "Salem, kak dela?"
        ),
        ChatSender(
            dateSent = "11.04.2020, 4:19:57.401",
            author = "Azat",
            message = "Cәлем"
        ),
    )*/

}