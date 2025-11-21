package com.example.studify.ui

import android.app.Application
import com.example.studify.data.repository.ChatRepository
import com.example.studify.data.repository.UserRepository
import javax.inject.Inject

class chatlistVM @Inject constructor(application: Application,  private val userRepository: UserRepository){
    fun getChatlist()=userRepository.requestUserData()
        .subscribe({
                result->
            groupids.clear()
            groupids.addAll(result.result?.chatlist.forEach {  } ?: emptyList<String>())
        },{
                errorMsg->
            error.value = errorMsg.toString()
        })
}