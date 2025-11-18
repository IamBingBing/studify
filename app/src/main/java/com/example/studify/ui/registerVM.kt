package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class registerVM @Inject constructor( appliction: Application , studifyService: StudifyService) : ViewModel(){
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
    var expanded = mutableStateOf(false)

    fun onExpandedChange(isExpanded: Boolean) {
        expanded.value = isExpanded
    }

    fun onSexSelected(selection: Int) {
        sex.value = selection
    }


}
