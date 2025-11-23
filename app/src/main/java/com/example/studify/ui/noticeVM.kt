package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.AnnounceModel
import com.example.studify.data.repository.noticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class noticeVM @Inject constructor(
    application: Application,
    private val noticeRepository: noticeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = mutableStateOf(savedStateHandle["groupid"] ?: 0)
    val notices = mutableStateListOf<AnnounceModel.AnnounceContent>()
    val query = mutableStateOf("")
    val selectedNotice = mutableStateOf<AnnounceModel.AnnounceContent?>(null)
    val errorMessage = mutableStateOf<String?>(null)

    private val disposables = CompositeDisposable()

    init {
        // ViewModel 생성되자마자, 현재 groupId 기준으로 공지 불러오기
        loadNotices()
    }

    /** 현재 groupId 기준으로 공지 목록 불러오기 */
    fun loadNotices(id: Int = groupId.value) {
        if (id == 0) {
            errorMessage.value = "유효하지 않은 그룹입니다."
            notices.clear()
            return
        }

        errorMessage.value = null

        val disposable = noticeRepository.requestNoticeData(id)
            .subscribe({ model: AnnounceModel ->
                notices.clear()

                if (model.resultCode == "200") {
                    if (model.result.isNotEmpty()) {
                        notices.addAll(model.result)
                    } else {
                        // 정상 응답인데 공지가 없는 경우
                        errorMessage.value = "등록된 공지사항이 없습니다."
                    }
                } else {
                    errorMessage.value =
                        model.errorMsg.ifBlank { "공지사항을 불러오지 못했습니다." }
                }
            }, { error ->
                error.printStackTrace()
                errorMessage.value = "네트워크 오류가 발생했습니다."
                notices.clear()
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

    fun onNoticeClick(notice: AnnounceModel.AnnounceContent) {
        selectedNotice.value = notice
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
