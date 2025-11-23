package com.example.studify.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.MentorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class qnaVM @Inject constructor(
    private val repository: MentorRepository
) : ViewModel() {

    var items = mutableStateListOf<QnaItem>()
    var errorMsg = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var showDialog = mutableStateOf(false)
    var inputTitle = mutableStateOf("")
    var inputContent = mutableStateOf("")
    private val disposables = CompositeDisposable()
    private var currentGroupId = -1

    fun loadGroupQna(groupId: Int) {
        currentGroupId = groupId
        isLoading.value = true

        val d = repository.requestGroupMentorQnaData(groupId)
            .subscribe({ model ->
                isLoading.value = false
                if (model.resultCode == "200" && model.result != null) {
                    items.clear()
                    model.result!!.forEach { q ->
                        items.add(
                            QnaItem(
                                id = q.qnaid ?: 0,
                                title = q.qnatitle,
                                content = q.qnacontent,
                                answer = q.qnaanswer
                            )
                        )
                    }
                } else {
                    if (model.errorMsg.contains("데이터가 존재하지")) {
                        items.clear()
                    } else {
                        errorMsg.value = model.errorMsg
                    }
                }
            }, { error ->
                isLoading.value = false
                errorMsg.value = "통신 오류: ${error.message}"
            })
        disposables.add(d)
    }

    fun writeQna() {
        if (currentGroupId == -1) return
        if (inputTitle.value.isBlank()) {
            errorMsg.value = "제목을 입력해주세요."
            return
        }

        val d = repository.requestAddQna(currentGroupId, inputTitle.value, inputContent.value)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    inputTitle.value = ""
                    inputContent.value = ""
                    showDialog.value = false
                    loadGroupQna(currentGroupId)
                } else {
                    errorMsg.value = model.errorMsg
                }
            }, { error ->
                errorMsg.value = "작성 실패: ${error.message}"
            })
        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    data class QnaItem(val id: Long, val title: String, val content: String, val answer: String)
}