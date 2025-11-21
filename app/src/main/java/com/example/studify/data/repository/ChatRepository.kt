package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.localDB.Message
import com.example.studify.data.localDB.MessageDao
import com.example.studify.data.model.ChatModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(private val studifyService: StudifyService , private val messageDao: MessageDao) {
    fun requestChat(groupid : String): Single<ChatModel>{
        val params= HashMap<String,String>().apply {
            this["GROUPID"] = groupid
        }
        return studifyService.requestchat(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun loadChat(groupid: String): Flow<List<Message>> {
        return messageDao.getMessageByRoom(groupid)
    }
}