package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class matchingOptionMentorVM @Inject constructor(application: Application, studifyService: StudifyService):ViewModel(){
    var wantlearn = mutableStateOf("")
    var wantteach = mutableStateOf("")
}