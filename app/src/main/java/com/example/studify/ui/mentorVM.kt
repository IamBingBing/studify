package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class mentorVM @Inject constructor(application: Application,studifyService: StudifyService): ViewModel() {
    var groupName = mutableStateOf("멘토링 스터디 그룹")
    var mentorCanTeach = mutableStateOf("나 영어 잘함")
    var menteeWants = mutableStateOf("영어좀 알려주세요")
    var studySchedules = mutableStateListOf<String>("이때", "모여")
    var notices = mutableStateListOf<String>("공지", "써라")
    var mentorList = mutableStateListOf<MentorInfo>(
        MentorInfo(name = "멘토1", field = "백엔드 / 자료구조"),
        MentorInfo(name = "멘토2", field = "프론트엔드 / UI/UX")
    )
    var menteeList = mutableStateListOf<MenteeInfo>(
        MenteeInfo(name = "멘티1", goal = "코딩테스트 준비"),
        MenteeInfo(name = "멘티2", goal = "안드로이드 앱 출시")
    )
    var currentTab = mutableStateOf(0)
}
