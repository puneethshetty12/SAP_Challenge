package com.coresystems.codelab.view.create

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.coresystems.codelab.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_create_memo.*

/**
 * Activity that allows a user to create a new Memo.
 */
class CreateMemo : AppCompatActivity() {
    private lateinit var model: CreateMemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memo)
        setSupportActionBar(toolbar)
        model = ViewModelProviders.of(this).get(CreateMemoViewModel::class.java)
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
                model.updateMemo(memo_title.text.toString(), memo_description.text.toString())
                if (model.isMemoValid()) {
                    model.saveMemo()
                    finish()
                } else {
                    memo_title_container.error = model.getTitleError(this)
                    memo_description_container.error = model.getTextError(this)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
