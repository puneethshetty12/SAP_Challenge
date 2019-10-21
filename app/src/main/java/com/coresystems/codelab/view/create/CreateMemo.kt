package com.coresystems.codelab.view.create

import android.app.PendingIntent
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.coresystems.codelab.R
import com.coresystems.codelab.alert.NotificationReceiver
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_create_memo.*
import java.util.*


/**
 * Activity that allows a user to create a new Memo.
 */
class CreateMemo : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var model: CreateMemoViewModel
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private var reminderLatitude: Double = 0.0
    private var reminderLongitude: Double = 0.0
    private val MAPVIEW_BUNDLE_KEY = "AIzaSyCJkgMsqJxFkFxIjqqhQksccMBUAZnlQi8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memo)
        setSupportActionBar(toolbar)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        model = ViewModelProviders.of(this).get(CreateMemoViewModel::class.java)
        mapView = findViewById(R.id.memo_mapview)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_create_memo, menu)
        return true
    }

    /**
     * Handles actionbar interactions.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                println("Memo ID :"+createMemoId())
                model.updateMemo(memo_title.text.toString(), memo_description.text.toString(), reminderLatitude, reminderLongitude)
                if (model.isMemoValid()) {
                    model.saveMemo()
                    //createProximityAlert()
                    finish()
                } else {
                    memo_title_container.error = model.getTitleError(this)
                    memo_description_container.error = model.getTextError(this)
                    memo_location_container.error = model.getLocationError(this)
                    println("Location error :"+ (reminderLatitude == 0.0))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onMapReady(map: GoogleMap?) {
        println("Map ready")
        if (map != null) {
            mMap = map
            mMap!!.setOnMapClickListener(this)
        }

    }
    override fun onMapClick(point: LatLng?) {
        mMap.clear()
        println("Map tapped!"+point!!.latitude+" & "+point.longitude)
        reminderLatitude = point.latitude
        reminderLongitude = point.longitude
        var markerOptions = MarkerOptions().position(point)
        markerOptions.title("Selected point")
        mMap.addMarker(markerOptions)
        memo_location.setText("Location added")
    }

    private fun createMemoId(): Int{
        val calender = Calendar.getInstance()
        val hour = calender.get(Calendar.HOUR_OF_DAY)
        val min = calender.get(Calendar.MINUTE)
        val sec = calender.get(Calendar.SECOND)
        val milliSec = calender.get(Calendar.MILLISECOND)
        return Integer.parseInt(""+hour+""+min+""+sec+""+milliSec)
    }

    /**
     * Creates a pendingIntent and adds it to proximityAlert
     */
    private fun createProximityAlert(){
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(applicationContext,0,intent, 0)
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
