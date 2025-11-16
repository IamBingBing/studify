package com.example.studify.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class registerVM : ViewModel{
    var email = mutableStateOf<String>("")
    var sex = mutableStateOf<Int>(-1)
    var adress = mutableStateOf<String>("")
    var username = mutableStateOf<String>("")
    var group = mutableStateOf<String>("")
    var userid = mutableStateOf<String>("")
    var phone = mutableStateOf<String>("")
    var pw = mutableStateOf<String>("")
    var repw = mutableStateOf<String>("")


    var genderOptions = mutableStateOf<Int>(-1)
    var expanded = mutableStateOf<Int>(0)

    fun onSexSelected(selection: Int) {
        sex.value = selection
    }

    fun onExpandedChange(isExpanded: Int) {
        expanded.value = 0
    }
    constructor(){

    }
}
