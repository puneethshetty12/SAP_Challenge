package com.coresystems.codelab.view.create

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.widget.Toast
import com.coresystems.codelab.alert.NotificationReceiver
import com.coresystems.codelab.model.PROXIMITY_RADIUS

class ProximityAlert {

    companion object{
        val instance: ProximityAlert by lazy { ProximityAlert() }
        private lateinit var context: Context

        fun saveContext(context: Context){
            this.context = context
        }
    }

    @SuppressLint("MissingPermission")
    fun createProximityAlert(intentId: Int, messageTitle: String, messageDescription: String, lat: Double, lon: Double){
        val intent = Intent(context, NotificationReceiver::class.java)
        //println(memo_title.text.toString()+"-"+memo_description.text.toString())
        intent.putExtra("MESSAGE_TITLE",messageTitle)
        intent.putExtra("MESSAGE_DESCRIPTION",messageDescription)
        val pendingIntent = PendingIntent.getBroadcast(context,intentId,intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.addProximityAlert(lat, lon, PROXIMITY_RADIUS, -1,pendingIntent)
    }

    fun removeProximityAlert(intentId: Int){
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,intentId,intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeProximityAlert(pendingIntent)
        Toast.makeText(context,"Proximity alert deleted",Toast.LENGTH_SHORT).show()
    }
}