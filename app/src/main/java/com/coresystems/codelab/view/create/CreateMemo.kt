package com.coresystems.codelab.view.create

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.coresystems.codelab.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_create_memo.*





/**
 * Activity that allows a user to create a new Memo.
 */
class CreateMemo : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var model: CreateMemoViewModel
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private var reminderLatitude: Long = 0
    private var reminderLongitude: Long = 0
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
                model.updateMemo(memo_title.text.toString(), memo_description.text.toString(), reminderLatitude, reminderLongitude)
                if (model.isMemoValid()) {
                    model.saveMemo()
                    finish()
                } else {
                    memo_title_container.error = model.getTitleError(this)
                    memo_description_container.error = model.getTextError(this)
                    memo_location_container.error = model.getLocationError(this)
                    println("Location error :"+ (reminderLatitude == 0L))
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
        reminderLatitude = point.latitude.toLong()
        reminderLongitude = point.longitude.toLong()
        var markerOptions = MarkerOptions().position(point)
        markerOptions.title("Selected point")
        mMap.addMarker(markerOptions)
        memo_location.setText("Location added")
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
}
