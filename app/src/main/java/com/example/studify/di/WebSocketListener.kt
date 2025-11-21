package com.example.studify.di

import com.example.studify.Tool.Preferences
import com.example.studify.data.localDB.Message
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject
import java.sql.Timestamp

class ChatWebSocketClient (private val onNewMsg: (Message) ->Unit) : WebSocketListener() {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        sendLogin()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        val jsonObject = JSONObject(text)
        val msg = Message(
            id =0,
            CHATID = jsonObject.getInt("CHATID"),
            CHATNAME = jsonObject.getString("CHATNAME"),
            CHAT = jsonObject.getString("CHAT"),
            TIME = jsonObject.getString("TIME")
        )
        onNewMsg(msg)
    }
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
    }
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }
    fun connect(){
        val request = Request.Builder()
            .url("ws://13.209.227.200:8080")
            .build()
        webSocket = client.newWebSocket(request,this)
    }
    fun sendLogin() {
        val json = """{"USERID":"${Preferences.getString("USERID")}"}"""
        webSocket?.send(json)
    }
}
