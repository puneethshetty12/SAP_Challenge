package com.coresystems.codelab.view.home

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import com.coresystems.codelab.R
import com.coresystems.codelab.alert.NotificationHelper
import com.coresystems.codelab.alert.ProximityAlert
import com.coresystems.codelab.model.Android
import com.coresystems.codelab.model.Memo
import com.coresystems.codelab.view.create.CreateMemo
import com.coresystems.codelab.view.detail.BUNDLE_MEMO_ID
import com.coresystems.codelab.view.detail.ViewMemo
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.coroutines.experimental.launch

/**
 * The main activity of the app. Shows a list of recorded memos and lets the user add new memos.
 */
class Home : AppCompatActivity() {
    private lateinit var adapter: MemoAdapter
    private lateinit var model: HomeViewModel
    private lateinit var menuItemShowAll: MenuItem
    private lateinit var menuItemShowOpen: MenuItem

    companion object {
        const val NOTIFICATION_CHANNEL_NAME = "Memo notifications"
        const val NOTIFICATION_CHANNEL_DESC = "Notification channel for memos."
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            println("Version :"+Build.VERSION.SDK_INT)
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            } else {
                //do action
                println("Granted")
            }
        } else {
            //do action
            println("Granted")
        }
        NotificationHelper.createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_DEFAULT, false, NOTIFICATION_CHANNEL_NAME, NOTIFICATION_CHANNEL_DESC)
        //Setup observation of the memo list (that we'll update the adapter with once it changes)
        model = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        observeViewModel(model, false)

        //Setup the recycler view and the adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MemoAdapter(mutableListOf(), View.OnClickListener {
            //Implementation for when the user selects a row to show the detail view
            view ->
            val intent = Intent(this@Home, ViewMemo::class.java)
            intent.putExtra(BUNDLE_MEMO_ID, (view.tag as Memo).id)
            startActivity(intent)
        }, CompoundButton.OnCheckedChangeListener {
            //Implementation for when the user marks a memo as completed
            checkbox, isChecked ->
            model.updateMemo(checkbox.tag as Memo, isChecked)
            if(isChecked){
                ProximityAlert.instance.removeProximityAlert((checkbox.tag as Memo).intentId)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, (recyclerView.layoutManager as LinearLayoutManager).orientation))

        fab.setOnClickListener {
            //Handles clicks on the FAB button > creates a new Memo
            startActivity(Intent(this@Home, CreateMemo::class.java))
        }
    }

    private fun observeViewModel(@NonNull viewModel: HomeViewModel, showAll: Boolean) {
        launch(Android) {
            val observables = if (showAll) viewModel.getAllMemos().await() else viewModel.getOpenMemos().await()
            //Update the model with the observables
            viewModel.setMemos(observables, this@Home)
            observables.observe(this@Home, Observer { memoList ->
                if (memoList != null) adapter.setItems(memoList)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        menuItemShowAll = menu.findItem(R.id.action_show_all)
        menuItemShowOpen = menu.findItem(R.id.action_show_open)
        return true
    }

    /**
     * Handles actionbar interactions.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_all -> {
                observeViewModel(model, true)
                //Switch available menu options
                menuItemShowAll.isVisible = false
                menuItemShowOpen.isVisible = true
                true
            }
            R.id.action_show_open -> {
                observeViewModel(model, false)
                //Switch available menu options
                menuItemShowOpen.isVisible = false
                menuItemShowAll.isVisible = true
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
