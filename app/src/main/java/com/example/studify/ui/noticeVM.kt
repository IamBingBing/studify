package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import com.example.studify.data.model.AnnounceModel
import com.example.studify.data.repository.noticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class noticeVM @Inject constructor(
    application: Application,
    private val noticeRepository: noticeRepository
) : ViewModel() {

    // 전체 공지 목록
    val notices = mutableStateListOf<AnnounceModel.AnnounceContent>()
    var query = mutableStateOf("")

    init {
        loadNotices(groupId = 1)
    }

    /** 공지 목록 불러오기 */
    fun loadNotices(groupId: Int) = noticeRepository.requestNoticeList(groupId)
            .subscribe({ model : List<AnnounceModel.AnnounceContent> ->
                notices.clear()

                if (model.result_code == "200"){

                }
            } , { error ->
                error.printStackTrace()
                // TODO:
            })


    /** 검색어 변경 */
    fun onQueryChange(value: String) {
        query.value = value
    }

    /** 검색어 적용된 공지 리스트 */
    fun filteredNotices(): List<AnnounceModel.AnnounceContent> {
        val q = query.value.trim()
        if (q.isBlank()) return notices

        return notices.filter { item ->
            (item.announcename ?: "").contains(q, ignoreCase = true)
        }
    }
}

