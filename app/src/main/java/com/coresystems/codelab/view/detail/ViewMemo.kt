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

    private var reminderLongitude: Long = 0
    private var reminderLatitude: Long = 0
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private val MAPVIEW_BUNDLE_KEY = "AIzaSyCJkgMsqJxFkFxIjqqhQksccMBUAZnlQi8"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_memo)
        setSupportActionBar(toolbar)
        //Initialize views with the passed memo id
        val model = ViewModelProviders.of(this).get(ViewMemoViewModel::class.java)
        var mapViewBundle: Bundle?
        if (savedInstanceState == null) {
            launch(Android) {
                mapViewBundle = savedInstanceState!!.getBundle(MAPVIEW_BUNDLE_KEY)
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

                mapView.onCreate(mapViewBundle)
                mapView.getMapAsync(this@ViewMemo)
            }
        }
    }
    override fun onMapReady(map: GoogleMap?) {
        var markerOption = MarkerOptions()
        markerOption.position(LatLng(reminderLatitude.toDouble(), reminderLongitude.toDouble()))
        markerOption.title("Selected location.")
        map!!.addMarker(markerOption)
    }
}
