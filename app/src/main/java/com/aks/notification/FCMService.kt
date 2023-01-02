package com.aks.notification

import android.app.*
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URL


class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    var bitmap : Bitmap?=null
    var bitmap2 : Bitmap?= null
    var msgId : String?=null

    override fun onMessageReceived(message: RemoteMessage) {

//        val imageUrl = message.notification?.imageUrl
        val sharedPreferences = getSharedPreferences("My_pref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id",message.data["id"])
        editor.apply()
        val imageUrl = message.data["image_url_1"]
        val imageUrl2 = message.data["image_url_2"]
        msgId = message.data["id"]
        imageUrl?.let {
            val url = URL(it.toString())
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }

        imageUrl2?.let {
            val url = URL(it.toString())
            bitmap2 = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }


        showNotification()
        super.onMessageReceived(message)
    }
    private fun showNotification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, NotificationActivity::class.java)
        intent.putExtra("warning", "Hey Anil")
        intent.putExtra("id",msgId)

        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(NotificationActivity::class.java)
        stackBuilder.addNextIntentWithParentStack(intent)

        val contentIntent = stackBuilder
            .getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT
                        or PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", "Anil", importance).apply {
                description = "Notification Description"
            }
            // Register the channel with the system
            manager.createNotificationChannel(channel)
        }

        var builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(bitmap2)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(contentIntent)
        val notification = builder.build()
        notification.flags =
            notification.flags or (Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR)
        msgId?.toInt()?.let { manager.notify(it, notification) }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

}