package com.example.studify.di

import com.example.studify.Tool.Preferences
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import javax.inject.Inject

class ChatWebSocketClient @Inject constructor(private val client : OkHttpClient,private val webSocketListener: chatWebSocketListener) {
    private var webSocket: WebSocket? = null

    fun connect(){
        val request = Request.Builder()
            .url("ws://13.209.227.200:8080")
            .build()
        webSocket = client.newWebSocket(request,webSocketListener)
    }
    fun sendLogin() {
        val json = """{"USERID":"${Preferences.getLong("USERID")}"}"""
        webSocket?.send(json)
    }
    fun disconnect() {
        webSocket?.close(1000,"Client disconnected")
        webSocket = null
    }
}