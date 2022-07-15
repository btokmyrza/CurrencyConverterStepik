package kz.btokmyrza.currencyconverterv2.presentation.models

data class ChatReceiver(
    val dateSent: Map<String, MutableMap<String, String>>,
    var author: String,
    var message: String
) : ChatItem
