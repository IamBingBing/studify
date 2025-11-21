package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import com.example.studify.data.localDB.Message
import com.example.studify.data.repository.ChatRepository
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class chatingRoomVM @Inject constructor(application: Application, private val chatRepository: ChatRepository, private val userRepository: UserRepository,savedStateHandle: SavedStateHandle): ViewModel() {
    var groupids = mutableStateListOf("")
    var groupid = mutableStateOf(savedStateHandle["roomid"]?:"");
    var error = mutableStateOf("")
    init {
        var messages = chatRepository.loadChat(groupid.toString())
    }

    fun getGroup
}