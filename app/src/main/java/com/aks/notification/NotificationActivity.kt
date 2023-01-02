package com.aks.notification

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val textView: TextView = findViewById(R.id.txtMessage)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        intent.getStringExtra("id")?.toInt()?.let { manager.cancel(it) }

        textView.text = intent.getStringExtra("warning")
        Toast.makeText(
            this,
            getSharedPreferences("My_pref", MODE_PRIVATE).getString("id","Not found").toString(),
            Toast.LENGTH_SHORT
        ).show()
    }
}