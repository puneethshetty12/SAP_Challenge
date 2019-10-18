package com.coresystems.codelab.repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.coresystems.codelab.model.Memo

/**
 * The Dao representation of a Memo.
 *
 * Created by coresystems on 20.09.17.
 */
@Dao
interface MemoDao {

    /**
     * @return all memos that are currently in the database.
     */
    @Query("SELECT * FROM memo")
    fun getAll(): LiveData<List<Memo>>

    /**
     * @return all memos that are currently in the database and have not yet been marked as "done".
     */
    @Query("SELECT * FROM memo WHERE isDone = 0")
    fun getOpen(): LiveData<List<Memo>>

    /**
     * Inserts the given Memo into the database. We currently do not support updating of memos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    /**
     * @return the memo whose id matches the given id.
     */
    /*
    TODO there is a bug in kotlin where it renames parameters to arg0 (or other values - see gradle build error log)
    here is the related issue over at jetbrains: https://youtrack.jetbrains.com/issue/KT-18048
     */
    @Query("SELECT * FROM memo WHERE id = :arg0")
    fun getMemoById(memoId: Long): Memo
}