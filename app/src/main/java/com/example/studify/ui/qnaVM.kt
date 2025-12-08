/*
package com.example.studify.ui

import android.util.Log
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

    // 글쓰기 상태
    var showDialog = mutableStateOf(false)
    var inputTitle = mutableStateOf("")
    var inputContent = mutableStateOf("")

    private val disposables = CompositeDisposable()
    private var currentGroupId: Long = -1L

    // ✅ 멘토방 여부 판단용 플래그
    private var isMentorRoom: Boolean = false

    // ✅ 멘토방용 QNA 로드
    fun loadMentorQna(mentorId: Long) {
        currentGroupId = mentorId
        isMentorRoom = true // 멘토방 플래그 설정
        isLoading.value = true

        val d = repository.requestMentorQnaData(mentorId)
            .subscribe({ model ->
                isLoading.value = false
                if (model.resultCode == "200" && model.result != null) {
                    items.clear()
                    model.result!!.forEach { q ->
                        items.add(QnaItem(
                            id = q.qnaid?.toLongOrNull() ?: 0L,
                            title = q.qnatitle,
                            content = q.qnacontent,
                            answer = q.qnaanswer
                        ))
                    }
                }
            }, { error ->
                isLoading.value = false
                errorMsg.value = "로딩 실패"
                Log.e("QnaVM", "멘토 QNA 로딩 에러: ${error.message}")
            })
        disposables.add(d)
    }

    // ✅ 일반 그룹용 QNA 로드 (기존 함수)
    fun loadGroupQna(groupId: Long) {
        currentGroupId = groupId
        isMentorRoom = false // 일반 그룹 플래그 설정
        isLoading.value = true

        val d = repository.requestGroupMentorQnaData(groupId)
            .subscribe({ model ->
                isLoading.value = false
                if (model.resultCode == "200" && model.result != null) {
                    items.clear()
                    model.result!!.forEach { q ->
                        items.add(QnaItem(
                            id = q.qnaid?.toLongOrNull() ?: 0L,
                            title = q.qnatitle,
                            content = q.qnacontent,
                            answer = q.qnaanswer
                        ))
                    }
                }
            }, { error ->
                isLoading.value = false
                errorMsg.value = "로딩 실패"
                Log.e("QnaVM", "그룹 QNA 로딩 에러: ${error.message}")
            })
        disposables.add(d)
    }

    // [질문 등록]
    fun writeQna() {
        Log.d("QnaVM", "글쓰기 시도: ID=$currentGroupId, IsMentor=$isMentorRoom, Title=${inputTitle.value}")

        if (currentGroupId == -1L) return

        // ✅ 멘토방이면 멘토 전용 API 호출
        val d = if (isMentorRoom) {
            repository.requestAddMentorQna(currentGroupId, inputTitle.value, inputContent.value)
        } else {
            repository.requestAddQna(currentGroupId, inputTitle.value, inputContent.value)
        }

        d.subscribe({ model ->
            Log.d("QnaVM", "응답: Code=${model.resultCode}, Msg=${model.errorMsg}")

            if (model.resultCode == "200") {
                inputTitle.value = ""
                inputContent.value = ""
                showDialog.value = false

                // 목록 갱신
                if (isMentorRoom) {
                    loadMentorQna(currentGroupId)
                } else {
                    loadGroupQna(currentGroupId)
                }
            } else {
                errorMsg.value = model.errorMsg
            }
        }, { error ->
            Log.e("QnaVM", "통신 에러: ${error.message}")
            errorMsg.value = "작성 실패"
        }).let { disposables.add(it) }
    }

    // [답변 등록]
    fun addAnswer(qnaId: Long, answerContent: String) {
        Log.d("QnaVM", "답변 등록 시도: QnaID=$qnaId, Content=$answerContent")

        val d = repository.requestAddAnswer(qnaId.toInt(), answerContent)
            .subscribe({ model ->
                Log.d("QnaVM", "답변 응답: Code=${model.resultCode}, Msg=${model.errorMsg}")

                if (model.resultCode == "200") {
                    if (isMentorRoom) {
                        loadMentorQna(currentGroupId)
                    } else {
                        loadGroupQna(currentGroupId)
                    }
                } else {
                    errorMsg.value = "답변 등록 실패: ${model.errorMsg}"
                }
            }, { error ->
                Log.e("QnaVM", "답변 통신 에러: ${error.message}")
                errorMsg.value = "답변 등록 실패"
            })
        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    data class QnaItem(val id: Long, val title: String, val content: String, val answer: String)
}
*/