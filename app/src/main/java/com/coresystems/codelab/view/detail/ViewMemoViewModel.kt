package com.coresystems.codelab.view.detail

import android.arch.lifecycle.ViewModel
import com.coresystems.codelab.model.Memo
import com.coresystems.codelab.repository.Repository
import kotlinx.coroutines.experimental.Deferred
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * ViewModel for matching ViewMemo view.
 *
 * Created by coresystems on 23.09.17.
 */
class ViewMemoViewModel : ViewModel() {

    /**
     * Loads the memo whose id matches the given memoId from the database.
     */
    suspend fun getMemo(memoId: Long): Deferred<Memo> = bg { Repository.instance.getMemoById(memoId) }
}