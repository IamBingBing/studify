package com.example.studify.data.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages : List<Message>)
    @Query(" SELECT * FROM message WHERE CHATID = :chatid ")
    fun getMessageByRoom(chatid : Long): Flow<List<Message>>

}