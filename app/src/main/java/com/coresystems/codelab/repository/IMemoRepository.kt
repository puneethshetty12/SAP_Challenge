package com.coresystems.codelab.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.NonNull
import com.coresystems.codelab.model.Memo

/**
 * Interface for a repository offering memo related CRUD operations.
 *
 * Created by coresystems on 23.09.17.
 */
interface IMemoRepository {

    /**
     * Saves the given memo to the database.
     */
    fun saveMemo(@NonNull memo: Memo)

    /**
     * @return all memos currently in the database.
     */
    fun getAll(): LiveData<List<Memo>>

    /**
     * @return all memos currently in the database, except those that have been marked as "done".
     */
    fun getOpen(): LiveData<List<Memo>>

    /**
     * @return the memo whose id matches the given id.
     */
    fun getMemoById(id: Long): Memo
}