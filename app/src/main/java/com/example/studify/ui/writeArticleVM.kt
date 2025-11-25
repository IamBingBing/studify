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
class writeArticleVM @Inject constructor(
    application: Application,
    private val noticeRepository: noticeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = mutableStateOf(
        savedStateHandle.get<String>("groupid") ?: "0"
    )

    val title = mutableStateOf("")
    val content = mutableStateOf("")
    val isPin = mutableStateOf(false)

    val isLoading = mutableStateOf(false)
    val errorMsg = mutableStateOf<String?>(null)

    private val disposables = CompositeDisposable()

    fun canSave(): Boolean =
        title.value.isNotBlank() && content.value.isNotBlank()

    fun saveNotice(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val gid = groupId.value

        if (gid.isBlank() || gid == "0") {
            onError("유효하지 않은 그룹입니다.")
            return
        }

        if (!canSave()) {
            onError("제목과 내용을 입력해 주세요.")
            return
        }

        isLoading.value = true
        errorMsg.value = null

        val d = noticeRepository.addNotice(
            groupId = gid,
            title = title.value,
            content = content.value,
            isPin = isPin.value
        ).subscribe({ result ->
            isLoading.value = false
            if (result.resultCode == "200") {
                onSuccess()
            } else {
                val msg = result.errorMsg ?: "공지 등록에 실패했습니다."
                errorMsg.value = msg
                onError(msg)
            }
        }, { e ->
            isLoading.value = false
            e.printStackTrace()
            val msg = "서버 통신에 실패했습니다."
            errorMsg.value = msg
            onError(msg)
        })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
