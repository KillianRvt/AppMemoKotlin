package com.killianrvt.memoapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import dao.MemosDAO

@Database(entities = [MemoDTO::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun memosDAO(): MemosDAO?

}