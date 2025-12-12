package com.example.studify.data.localDB

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [Message::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}