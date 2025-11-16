package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import com.example.studify.data.model.AnnounceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class noticeVM @Inject constructor(
    application: Application,
    studifyService: StudifyService
) : ViewModel() {

    // 전체 공지 목록 (서버/DB에서 받아온 결과가 들어갈 곳)
    val notices = mutableStateListOf<String>()
    var query = mutableStateOf("")

    init {
        // 화면 처음 들어올 때 공지 로드
        loadNotices()
    }

    fun loadNotices() {
        //TODO: 공지목록 불러오기
    }

    fun onQueryChange(value: String) {
        query.value = value
    }

    /*private requestAnnouce ( groupid )<model> {

        model.result.foreach(){
            it ->
            if ( it.result.is_pin  )
            notices.add(it.result.announcename , it.result.is_pin)
        }
    }
*/
}



