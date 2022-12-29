package com.aks.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnSend)

        button.setOnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val intent = Intent(this, NotificationActivity::class.java)
            intent.putExtra("warning", "Hey Anil")

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
                .setContentIntent(contentIntent)
            val notification = builder.build()
            notification.flags =
                notification.flags or (Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR)
            manager.notify(1, notification)
        }
    }
}