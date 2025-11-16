package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studify.data.StudifyService
import com.example.studify.data.model.AnnounceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class noticeDetailVM @Inject constructor(
    application: Application,
    studifyService: StudifyService
) : ViewModel() {

    // 화면에 보여줄 상태들
    val title = mutableStateOf("공지 제목")
    val content = mutableStateOf("여기가 공지 내용")
    val date = mutableStateOf("2025-11-09")

    fun loadNotice () {
    }

    fun deleteNotice() {

    }
}
