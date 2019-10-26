package com.coresystems.codelab.alert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val key =  LocationManager.KEY_PROXIMITY_ENTERING;
        val entering = intent!!.getBooleanExtra(key, false)
        if(entering){
            println("Entering")
        } else {
            println("Exiting")
        }
        val title = intent!!.getStringExtra("MESSAGE_TITLE")
        val desc = intent.getStringExtra("MESSAGE_DESCRIPTION")
        NotificationHelper.createNotification(context, title, desc)
    }
}