package kz.btokmyrza.currencyconverterv2.domain.enitities

import androidx.annotation.Keep
import com.google.firebase.database.ServerValue
import kz.btokmyrza.currencyconverterv2.presentation.models.ChatReceiver
import kz.btokmyrza.currencyconverterv2.presentation.models.ChatSender

@Keep
data class ChatMessage(
    val timestamp: Long? = 0,
    var author: String? = "",
    var message: String? = ""
)

fun ChatMessage.toChatSender(): ChatSender {
    return ChatSender(
        dateSent = mapOf("timestamp" to ServerValue.TIMESTAMP),
        author = author ?: "",
        message = message ?: ""
    )
}

fun ChatMessage.toChatReceiver(): ChatReceiver {
    return ChatReceiver(
        dateSent = mapOf("timestamp" to ServerValue.TIMESTAMP),
        author = author ?: "",
        message = message ?: ""
    )
}