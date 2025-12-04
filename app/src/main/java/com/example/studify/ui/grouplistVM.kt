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
class grouplistVM @Inject constructor(application: Application,  private val userRepository: UserRepository):ViewModel(){
    var grouplist = mutableStateMapOf<Long,String>()
    var mentorlist = mutableStateMapOf<Long,String>()
    var error = mutableStateOf("")
    fun getGrouplist()=userRepository.requestUserData()
        .subscribe({
                result->
            grouplist.clear()
            result.result?.grouplist?.forEach { group->
                grouplist[group.groupid!!]=group.groupname
            }
            result.result?.mentorlist?.forEach {
                mentor->
                mentorlist[mentor.mentorid!!] = mentor.mentorname
            }
        },{
                errorMsg->
            error.value = errorMsg.toString()
        })
    init {
        getGrouplist()
    }
}