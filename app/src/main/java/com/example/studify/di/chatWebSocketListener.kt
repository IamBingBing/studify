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
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class chatWebSocketListener @Inject constructor(private val messageHandler: ChatMessageHandler): WebSocketListener(){
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        val jsonObject = JSONObject(text)
        if (jsonObject.getString("CHANNEL") == "chatserver") {

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val localDateTime = LocalDateTime.parse(jsonObject["SENDTIME"] as String, formatter)

            val sendTimeMillis = localDateTime
                .atZone(ZoneId.of("Asia/Seoul"))
                .toInstant()
                .toEpochMilli()
            Log.e("websocket", text)
            val msg = Message(
                jsonObject.getLong("CHATID"),
                jsonObject["CHATNAME"] as String,
                jsonObject["CHAT"] as String,
                sendTimeMillis as Long,
                jsonObject["USERID"] as Long
            )
            messageHandler.onNewMessage(msg)
        }
        else if(jsonObject.getString("CHANNEL") == "fastmatch") {

        }

    }
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.e("websocket","connection failed",t)
    }
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }

}
