package com.example.studify.di

import android.util.Log
import com.example.studify.data.localDB.Message
import com.example.studify.data.model.ChatModel
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import javax.inject.Inject

class chatWebSocketListener @Inject constructor(private val messageHandler: ChatMessageHandler): WebSocketListener(){
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        val jsonObject= JSONObject(text)
        Log.e("websocket",text)
        val msg = Message(
            0,
            jsonObject.getLong("CHATID"),
            jsonObject["CHATNAME"] as String,
            jsonObject["CHAT"] as String,
            jsonObject["SENDERTIME"] as String )
        messageHandler.onNewMessage(msg)
    }
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.e("websocket","connection failed",t)
    }
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }

}
