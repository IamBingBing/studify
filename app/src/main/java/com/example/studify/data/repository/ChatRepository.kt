package com.example.studify.data.repository

import android.util.Log
import com.example.studify.data.StudifyService
import com.example.studify.data.localDB.Message
import com.example.studify.data.localDB.MessageDao
import com.example.studify.data.model.ChatModel
import com.example.studify.data.model.UpdateModel
import com.example.studify.di.ChatMessageHandler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(private val studifyService: StudifyService , private val messageDao: MessageDao ):ChatMessageHandler {
    private val scope = CoroutineScope(Dispatchers.IO)
    fun requestChat(chatid : String): Single<ChatModel>{
        val params= HashMap<String,String>().apply {
            this["CHATID"] = chatid
        }
        return studifyService.requestchat(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun loadChat(chatid: Long): Flow<List<Message>> {
        return messageDao.getMessageByRoom(chatid)
    }
    fun UpdateChat(chatid: String, chat: String,chatname:String): Single<UpdateModel> {
        val params = HashMap<String,String>().apply {
            this["CHATID"] = chatid
            this["CHAT"] = chat
            this["CHATNAME"] = chatname
        }
        return studifyService.UpdateChat(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    suspend fun insertMessage(msg:Message){
        messageDao.insertMessage(msg)
        Log.e("insertmessage",msg.toString());
    }
    override fun onNewMessage(message: Message) {
        scope.launch {
            insertMessage(message)
        }
    }
}