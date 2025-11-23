package com.example.studify.data.localDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
@Entity(tableName = "message")
data class Message (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "CHATID")
    val CHATID : Long,
    @ColumnInfo(name = "CHATNAME")
    val CHATNAME : String,
    @ColumnInfo(name = "CHAT")
    val CHAT : String,
    @ColumnInfo("TIME")
    val TIME : String
)