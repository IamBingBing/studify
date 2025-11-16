package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class groupVM @Inject constructor(application: Application,studifyService: StudifyService):ViewModel() {

    // 그룹 기본 정보
    var groupName = mutableStateOf("Ctrl + F")
    var groupGoal = mutableStateOf("잠은 죽어서 자자")
    var hashTags = mutableStateOf(listOf("프론트엔드개발", "알고리즘"))

    var currentTab = mutableStateOf(0)


    fun init() {
        // TODO:
    }


}