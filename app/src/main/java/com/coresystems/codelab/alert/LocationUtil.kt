package com.coresystems.codelab.alert

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*

object LocationUtil {

    fun getLocalName(context: Context, lat: Double, lon: Double): String? {
        var geocoder = Geocoder(context, Locale.getDefault())
        try {
            var location = geocoder.getFromLocation(lat,lon, 1)
            var obj: Address = location.get(0)
            var add = obj.getAddressLine(0)
            println(add)
            return add
        } catch (e: Exception){
            println("Error getting location name -"+e)
            return null
        }
    }
}