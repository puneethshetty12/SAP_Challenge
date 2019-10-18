package com.coresystems.codelab.model

import android.arch.lifecycle.LiveData

/**
 * LiveData implementation for Memos.
 *
 * Created by coresystems on 20.09.17.
 */
class MemoLiveData : LiveData<List<Memo>>() {

    override fun setValue(value: List<Memo>?) {
        super.setValue(value)
    }
}