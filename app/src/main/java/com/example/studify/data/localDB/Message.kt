package com.example.studify.data.localDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
@Entity(tableName = "message")
data class Message (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,

    val CHATID : Int,
    val CHATNAME : String,
    val CHAT : String,
    val TIME : Timestamp
)