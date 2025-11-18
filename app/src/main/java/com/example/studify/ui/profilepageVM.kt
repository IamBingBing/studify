package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class profilepageVM @Inject constructor(application: Application,studifyService: StudifyService): ViewModel() {
    var userName = mutableStateOf<String>("")
    var studyStyle = mutableStateOf<String>("")
    var mannerScore = mutableStateOf<Double>(0.0)
    var reviewTags = mutableStateListOf<String>("")
    var studyHistory = mutableStateOf("")

    }