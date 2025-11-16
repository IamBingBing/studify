package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class mypageVM @Inject constructor(application: Application,studifyService: StudifyService): ViewModel() {
    var isEditing = mutableStateOf(false)

    var name = mutableStateOf("강명묵")
    var email = mutableStateOf("my@naver.com")
    var group = mutableStateOf("A 그룹")
    var sex = mutableStateOf("남자")
    var address = mutableStateOf("경기도 파주시")
    var tendency = mutableStateOf("여유로움")
    var point = mutableStateOf("1000P")

}
