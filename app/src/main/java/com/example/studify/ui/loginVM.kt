package com.example.studify.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class loginVM : ViewModel {
    val loginid =mutableStateOf<String>("")
    val password = mutableStateOf<String>("")
    val autologin=mutableStateOf<Boolean>(false)
    constructor() {
        requestLogin()
        init()
    }
    fun init(){
        //TODO
    }
    fun requestLogin(){

    }
}
