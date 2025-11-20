package com.example.studify.di

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class ChatWebSocketClient : WebSocketListener() {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
    }
    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        var data = bytes.toByteArray().toString(Charsets.UTF_8)
        val result:Map<String,Any> = Gson().fromJson(data, object: TypeToken<Map<String,Any>>(){}.type)
        result["GROUPID"]
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
    fun sendLogin(userId: String) {
        val json = """{"USERID":"$userId"}"""
        webSocket?.send(json)
    }
}
