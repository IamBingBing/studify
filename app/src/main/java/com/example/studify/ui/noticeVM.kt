package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import com.example.studify.data.model.AnnounceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class Notice(
    val id: Int,
    val title: String,
    val isPinned: Boolean = false,
    val date: Long = 0L
)
@HiltViewModel
class noticeVM @Inject constructor(
    application: Application,
    studifyService: StudifyService
) : ViewModel() {

    // 전체 공지 목록 (서버/DB에서 받아온 결과가 들어갈 곳)
    val notices = mutableStateListOf<Notice>()
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

    /** 검색어 적용된 전체 목록 */
    private fun filtered(): List<Notice> {
        val q = query.value.trim()
        if (q.isBlank()) return notices

        return notices.filter { item ->
            val title = item.title.orEmpty()
            title.contains(q, ignoreCase = true)
        }
    }

    /** 핀 고정된 공지 리스트 */
    fun pinnedNotices(): List<Notice> =
        filtered().filter { it.isPinned == true }

    /** 그 외 공지 리스트 (날짜 최신순) */
    fun otherNotices(): List<Notice> =
        filtered()
            .filter { it.isPinned != true }
            .sortedByDescending { it.date }
}



