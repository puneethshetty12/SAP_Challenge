package com.coresystems.codelab.view.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.coresystems.codelab.R
import com.coresystems.codelab.model.Memo

/**
 * Viewholder for Memos.
 *
 * Created by coresystems on 20.09.17.
 */
internal class MemoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.memoTitle)
    val text: TextView = view.findViewById(R.id.memoText)
    private val checkbox: CheckBox = view.findViewById(R.id.checkBox)

    /**
     * Updates the memo view with the given memo.
     */
    fun update(memo: Memo, onClick: View.OnClickListener, onCheckboxChanged: CompoundButton.OnCheckedChangeListener) {
        title.text = memo.title
        text.text = memo.description
        checkbox.isChecked = memo.isDone
        //We only let the user edit the checkbox if the item has not been marked as "done"
        checkbox.isEnabled = !memo.isDone
        checkbox.setOnCheckedChangeListener(onCheckboxChanged)
        //We need the memo if the user ticks the checkbox, so we can update the memo
        checkbox.tag = memo
        //This is needed if the user selects a given memo to show the detail screen
        itemView.tag = memo
        itemView.setOnClickListener(onClick)
    }
}