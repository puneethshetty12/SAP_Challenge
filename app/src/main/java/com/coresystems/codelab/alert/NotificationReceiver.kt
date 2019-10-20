package com.coresystems.codelab.alert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        println("Broadcasr receiver")
        NotificationHelper.createNotification(context,"sample data")
    }
}