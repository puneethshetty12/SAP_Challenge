package com.coresystems.codelab.view.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.coresystems.codelab.R
import com.coresystems.codelab.model.Android
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_create_memo.*
import kotlinx.coroutines.experimental.launch

const val BUNDLE_MEMO_ID: String = "memoId"

/**
 * Activity that allows a user to see the details of a memo.
 */
class ViewMemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_memo)
        setSupportActionBar(toolbar)
        //Initialize views with the passed memo id
        val model = ViewModelProviders.of(this).get(ViewMemoViewModel::class.java)
        if (savedInstanceState == null) {
            launch(Android) {
                val id = intent.getLongExtra(BUNDLE_MEMO_ID, -1)
                val pendingMemo = model.getMemo(id)
                val memo = pendingMemo.await()
                //Update the UI
                memo_title.setText(memo.title)
                memo_description.setText(memo.description)
                memo_title.isEnabled = false
                memo_description.isEnabled = false
            }
        }
    }
}
