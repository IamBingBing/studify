package com.example.studify.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studify.Tool.Preferences
import com.example.studify.data.StudifyService
import com.example.studify.data.localDB.Message
import com.example.studify.data.repository.ChatRepository
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class chatingRoomVM @Inject constructor(application: Application, private val chatRepository: ChatRepository, private val userRepository: UserRepository,savedStateHandle: SavedStateHandle): ViewModel() {
    val scope = viewModelScope
    var roomid = mutableStateOf(savedStateHandle["roomid"]?:"");
    var message :StateFlow<List<Message>> =
        chatRepository.loadChat(roomid.value.toLong())
            .stateIn(scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    var sendmessage = mutableStateOf<String>("")
    var error = mutableStateOf("")
    var reportText = mutableStateOf("")

    init {
        requestMessage()
    }
    fun sendMessage() = chatRepository.UpdateChat(roomid.value,sendmessage.value,Preferences.getString("USERNAME")!!)
        .subscribe(
            {result->
                if (result.resultCode == "200"){
                    sendmessage.value= ""
                }
        },{ error ->
                Log.e("CHATROOM", error.toString())
            }
        )
    fun requestMessage () = chatRepository.requestChat(roomid.value)
        .subscribe(
            {
                result->
                if(result.resultCode == "200"){

                    scope.launch {
                        chatRepository.insertMessages(result.list!!.map { msg ->
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val localDateTime = LocalDateTime.parse(msg.TIME!!.padEnd(19,'0'), formatter)
                            val sendTimeMillis = localDateTime
                                .atZone(ZoneId.of("Asia/Seoul"))
                                .toInstant()
                                .toEpochMilli()
                            Message(
                                msg.chatid!!,
                                msg.CHATNAME!!,
                                msg.CHAT!!,
                                sendTimeMillis,
                                msg.USERID!!
                            )
                        })
                    }
                }
            },
            {error->
                Log.e("CHATROOM",error.toString())
            }
        )
    fun report (usid:String,pt:String) = userRepository.reportUser(usid , pt)
        .subscribe(
            {
                result->
                if ( result.resultCode == "200"){
                }
            },{}
        )
}