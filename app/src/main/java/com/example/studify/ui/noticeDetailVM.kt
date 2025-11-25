package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.noticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class noticeDetailVM @Inject constructor(
    application: Application,
    private val noticeRepository: noticeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 네비게이션에서 넘어온 공지 ID (noticeid)
    private val announceId: Long =
        savedStateHandle.get<String>("noticeid")?.toLongOrNull() ?: 0L

    val title = mutableStateOf("공지 제목")
    val content = mutableStateOf("여기가 공지 내용")
    val date = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val errorMsg = mutableStateOf<String?>(null)

    private val disposables = CompositeDisposable()

    init {
        loadNotice()
    }

    fun loadNotice() {
        if (announceId == 0L) {
            errorMsg.value = "잘못된 공지 ID 입니다."
            return
        }

        isLoading.value = true
        errorMsg.value = null

        val d = noticeRepository.requestNoticeDetail(announceId)
            .subscribe({ model ->
                isLoading.value = false
                if (model.resultCode == "200" && model.result.isNotEmpty()) {
                    val item = model.result[0]
                    title.value = item.announceName ?: "(제목 없음)"
                    content.value = item.announceContent ?: "(내용 없음)"
                    date.value = item.announceDate ?: ""
                } else {
                    errorMsg.value = model.errorMsg.ifBlank { "공지 정보를 불러오지 못했습니다." }
                }
            }, { e ->
                e.printStackTrace()
                isLoading.value = false
                errorMsg.value = "서버 통신에 실패했습니다."
            })

        disposables.add(d)
    }

    fun deleteNotice(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        // TODO: 나중에 deleteNotice.php 만들면 여기에서 호출해서 삭제 처리
        onError("삭제 기능은 아직 구현되지 않았습니다.")
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
