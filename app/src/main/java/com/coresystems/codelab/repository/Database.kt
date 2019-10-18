package com.coresystems.codelab.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.coresystems.codelab.model.Memo

/**
 * That database that is used to store information.
 *
 * Created by coresystems on 20.09.17.
 */
@Database(entities = arrayOf(Memo::class), version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun getMemoDao(): MemoDao
}