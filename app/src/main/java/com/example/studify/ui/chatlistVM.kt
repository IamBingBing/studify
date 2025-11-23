package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.ChatRepository
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class chatlistVM @Inject constructor(application: Application,  private val userRepository: UserRepository):ViewModel(){
    var chatlist = mutableStateMapOf<Long,String>()
    var error = mutableStateOf("")
    fun getChatlist()=userRepository.requestUserData()

        .subscribe({
            result->
            chatlist.clear()
            result.result?.chatlist?.forEach { chat->
                chatlist.put(chat.chatid!!,chat.roomname)
            }
        },{
            errorMsg->
            error.value = errorMsg.toString()
        })
    init {
        getChatlist()
    }
}