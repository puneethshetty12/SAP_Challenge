package com.coresystems.codelab.repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.WorkerThread
import com.coresystems.codelab.model.Memo

/**
 * The repository is used to retrieve data from a data source.
 *
 * Created by coresystems on 20.09.17.
 */
class Repository private constructor() : IMemoRepository {

    private object Holder {
        val INSTANCE = Repository()
    }

    companion object {
        const val DATABASE_NAME: String = "codelab"
        val instance: Repository by lazy { Holder.INSTANCE }

        private lateinit var database: Database

        fun create(context: Context) {
            database = Room.databaseBuilder(context, Database::class.java, DATABASE_NAME).build()
        }
    }

    @WorkerThread
    override fun saveMemo(@NonNull memo: Memo) {
        database.getMemoDao().insert(memo)
    }

    @WorkerThread
    override fun getOpen(): LiveData<List<Memo>> = database.getMemoDao().getOpen()

    @WorkerThread
    override fun getAll(): LiveData<List<Memo>> = database.getMemoDao().getAll()

    @WorkerThread
    override fun getMemoById(id: Long): Memo = database.getMemoDao().getMemoById(id)
}