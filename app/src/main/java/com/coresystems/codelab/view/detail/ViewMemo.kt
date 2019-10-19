package com.coresystems.codelab.view.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.coresystems.codelab.R
import com.coresystems.codelab.model.Android
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_create_memo.*
import kotlinx.coroutines.experimental.launch

const val BUNDLE_MEMO_ID: String = "memoId"

/**
 * Activity that allows a user to see the details of a memo.
 */
class ViewMemo : AppCompatActivity(), OnMapReadyCallback {

    private var reminderLongitude: Double = 0.0
    private var reminderLatitude: Double = 0.0
    private lateinit var mapView: MapView
    private var mMap: GoogleMap? = null
    private val MAPVIEW_BUNDLE_KEY = "AIzaSyCJkgMsqJxFkFxIjqqhQksccMBUAZnlQi8"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_memo)
        setSupportActionBar(toolbar)
        //Initialize views with the passed memo id
        val model = ViewModelProviders.of(this).get(ViewMemoViewModel::class.java)
        var mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)
        mapView = findViewById(R.id.memo_mapview)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this@ViewMemo)
        if (savedInstanceState == null) {
            launch(Android) {
                val id = intent.getLongExtra(BUNDLE_MEMO_ID, -1)
                val pendingMemo = model.getMemo(id)
                val memo = pendingMemo.await()
                //Update the UI
                memo_title.setText(memo.title)
                memo_description.setText(memo.description)
                reminderLongitude = memo.reminderLongitude
                reminderLatitude = memo.reminderLatitude
                memo_title.isEnabled = false
                memo_description.isEnabled = false
                createMarker()
            }
        }
    }
    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        println("map ready")
    }

    private fun createMarker(){
        val markerOption = MarkerOptions()
        markerOption.position(LatLng(reminderLatitude, reminderLongitude))
        markerOption.title("Selected location.")
        mMap!!.addMarker(markerOption)
        println("Marker added")
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
