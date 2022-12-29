package com.aks.notification

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val textView : TextView = findViewById(R.id.txtMessage)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1)

        textView.text = intent.getStringExtra("warning")
    }
}