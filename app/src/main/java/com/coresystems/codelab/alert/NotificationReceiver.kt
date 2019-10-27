package com.coresystems.codelab.alert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent!!.getStringExtra("MESSAGE_TITLE")
        val desc = intent.getStringExtra("MESSAGE_DESCRIPTION")
        NotificationHelper.createNotification(context, title, desc)
    }
}