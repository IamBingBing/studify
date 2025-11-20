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
class noticeVM @Inject constructor(
    application: Application,                 // 지금은 안 쓰지만 두어도 됨
    private val noticeRepository: noticeRepository
) : ViewModel() {

    // 전체 공지 목록 (서버에서 받아온 공지들)
    val notices = mutableStateListOf<AnnounceModel.AnnounceContent>()

    // 검색어 상태
    val query = mutableStateOf("")

    // RxJava 구독 관리용
    private val disposables = CompositeDisposable()

    init {
        // TODO: 나중에 실제 groupId 넣어주면 됨
        loadNotices(groupId = 1)
    }

    /** 공지 목록 불러오기 */
    fun loadNotices(groupId: Int) {
        val disposable = noticeRepository.requestNoticeList(groupId)
            .subscribe({ model: AnnounceModel ->
                // 기존 리스트 비우고 새로 채우기
                notices.clear()

                if (model.resultCode == "200") {
                    // 서버에서 보낸 공지 리스트 추가
                    notices.addAll(model.result)
                } else {
                    // 에러 코드일 때: 필요하면 에러 메시지 상태 따로 만들어서 UI에 표시
                    // model.errorMsg 사용 가능
                }
            }, { error ->
                error.printStackTrace()
                // TODO: 네트워크 에러 처리 (스낵바, 토스트 등)
            })

        disposables.add(disposable)
    }

    /** 검색어 변경 */
    fun onQueryChange(value: String) {
        query.value = value
    }

    /** 검색어가 적용된 공지 리스트 */
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

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
