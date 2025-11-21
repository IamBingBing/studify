package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.AnnounceModel
import com.example.studify.data.repository.noticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class noticeVM @Inject constructor(application: Application, private val noticeRepository: noticeRepository) : ViewModel() {

    // 전체 공지 목록
    val notices = mutableStateListOf<AnnounceModel.AnnounceContent>()

    // 검색어
    val query = mutableStateOf("")

    // 클릭된 공지 (선택된 공지)
    val selectedNotice = mutableStateOf<AnnounceModel.AnnounceContent?>(null)

    private val disposables = CompositeDisposable()

    init {
        loadNotices(groupId = 1)
    }

    fun loadNotices(groupId: Int) {
        val disposable = noticeRepository.requestNoticeData(groupId)
            .subscribe({ model: AnnounceModel ->
                notices.clear()

                if (model.resultCode == "200") {
                    notices.addAll(model.result)
                } else {
                    // TODO: 에러 메시지 처리 (model.errorMsg)
                }
            }, { error ->
                error.printStackTrace()
                // TODO: 네트워크 에러 처리
            })

        disposables.add(disposable)
    }

    fun onQueryChange(value: String) {
        query.value = value
    }

    fun filteredNotices(): List<AnnounceModel.AnnounceContent> {
        val q = query.value.trim()
        if (q.isBlank()) return notices

        return notices.filter { item ->
            val name = item.announceName ?: ""
            val content = item.announceContent ?: ""
            name.contains(q, ignoreCase = true) ||
                    content.contains(q, ignoreCase = true)
        }
    }

    /** 공지 클릭 시 호출할 함수 */
    fun onNoticeClick(notice: AnnounceModel.AnnounceContent) {
        selectedNotice.value = notice
        // 나중에 여기서 로그 남기거나, 상세 화면용으로 사용 가능
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
