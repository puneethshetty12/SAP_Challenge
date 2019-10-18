package com.coresystems.codelab.view.home

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.coresystems.codelab.model.Memo
import com.coresystems.codelab.repository.Repository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

/**
 * ViewModel for the Home Activity.
 *
 * Created by coresystems on 23.09.17.
 */
class HomeViewModel : ViewModel() {
    private var memos: LiveData<List<Memo>> = EmptyLiveData()

    suspend fun getAllMemos(): Deferred<LiveData<List<Memo>>> {
        return async(CommonPool) {
            Repository.instance.getAll()
        }
    }

    suspend fun getOpenMemos(): Deferred<LiveData<List<Memo>>> {
        return async(CommonPool) {
            Repository.instance.getOpen()
        }
    }

    fun updateMemo(memo: Memo, isChecked: Boolean) {
        async(CommonPool) {
            //We'll only forward the update if the memo has been checked, since we don't offer to uncheck memos right now
            if (isChecked) {
                Repository.instance.saveMemo(memo.copy(isDone = isChecked))
            }
        }
    }

    fun setMemos(newMemos: LiveData<List<Memo>>, lifecycleOwner: LifecycleOwner) {
        //Remove observers from existing memos
        memos.removeObservers(lifecycleOwner)
        //Update the memos that are being observed with the given ones
        memos = newMemos
    }
}

/**
 * Dummy class to avoid NPEs. This is basically an empty set of live data.
 */
private class EmptyLiveData : LiveData<List<Memo>>()