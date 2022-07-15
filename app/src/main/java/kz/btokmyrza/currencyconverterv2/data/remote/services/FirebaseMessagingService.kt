package kz.btokmyrza.currencyconverterv2.data.remote.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kz.btokmyrza.currencyconverterv2.R
import kz.btokmyrza.currencyconverterv2.presentation.MainActivity

private const val CHANNEL_ID = "1234"

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        createNotificationChannel()
        displayNotification(message)
    }

    override fun onNewToken(token: String) = Unit

    private fun displayNotification(message: RemoteMessage) {
        val title = message.data["title"]
        val body = message.data["body"]

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_icon_bw)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(getPendingIntent())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(Int.MIN_VALUE, notification)
    }

    private fun getPendingIntent(): PendingIntent {
        return NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.navigation_favorites)
            .createPendingIntent()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Домашнее задание 33.6"
            val descriptionText = "С помощью FCM Токена отправить сообщение с title/body"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
