package com.example.studify.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class registerVM : ViewModel{
    var email = mutableStateOf<String>("")
    var sex = mutableStateOf<String>("")
    var adress = mutableStateOf<String>("")
    var username = mutableStateOf<String>("")
    var group = mutableStateOf<String>("")
    var userid = mutableStateOf<String>("")
    var phone = mutableStateOf<String>("")
    var pw = mutableStateOf<String>("")
    var repw = mutableStateOf<String>("")
    

    var genderOptions = mutableStateOf(listOf("남", "여"))
    

    var expanded = mutableStateOf(false)

    fun onSexSelected(selection: String) {
        sex.value = selection
    }

    fun onExpandedChange(isExpanded: Boolean) {
        expanded.value = isExpanded
    }
    constructor(){

    }
}
