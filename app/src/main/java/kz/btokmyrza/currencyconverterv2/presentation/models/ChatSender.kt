package kz.btokmyrza.currencyconverterv2.presentation.models

data class ChatSender(
    val dateSent: Map<String, MutableMap<String, String>>,
    var author: String,
    var message: String,
) : ChatItem
