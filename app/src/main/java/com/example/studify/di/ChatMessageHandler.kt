package com.example.studify.di

import com.example.studify.data.localDB.Message
import com.example.studify.data.model.ChatModel

interface ChatMessageHandler {
    fun onNewMessage(message: Message)
}