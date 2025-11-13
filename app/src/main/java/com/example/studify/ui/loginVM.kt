package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class loginVM @Inject constructor(application: Application,studifyService: StudifyService): ViewModel() {
    var loginid =mutableStateOf<String>("")
    var password = mutableStateOf<String>("")
    var autologin=mutableStateOf<Boolean>(false)

    val userRepository = UserRepository(studifyService)
    fun init() {

    }
    private fun requestlogin(loginid :String, password: String) = userRepository.requestLogin(loginid ,password)
        .subscribe {
            if (it != null){
                it.result
            }

        }
}
