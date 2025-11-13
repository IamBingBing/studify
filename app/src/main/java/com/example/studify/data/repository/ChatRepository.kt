package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.ChatModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(private val studifyService: StudifyService) {
    fun requestChat(roomid : Int): Single<ChatModel>{
        val params= HashMap<String,Int>().apply {
            this["ROOMID"] = roomid
        }
        return studifyService.requestchat(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}