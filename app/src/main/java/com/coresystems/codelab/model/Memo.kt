package com.coresystems.codelab.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Represents a memo.
 *
 * Created by coresystems on 20.09.17.
 */
@Entity(tableName = "memo")
data class Memo(
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ColumnInfo(name = "title")
        var title: String = EMPTY_STRING,
        @ColumnInfo(name = "description")
        var description: String = EMPTY_STRING,
        @ColumnInfo(name = "reminderDate")
        var reminderDate: Long = 0,
        @ColumnInfo(name = "reminderLatitude")
        var reminderLatitude: Long = 0,
        @ColumnInfo(name = "reminderLongitude")
        var reminderLongitude: Long = 0,
        @ColumnInfo(name = "isDone")
        var isDone: Boolean = false
)
