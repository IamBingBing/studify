package com.example.studify.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.MentorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class qnaVM @Inject constructor(
    private val repository: MentorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val mentorId = mutableStateOf(savedStateHandle.get<String>("mentorid")?.toLongOrNull() ?: -1L)
    val items = mutableStateListOf<QnaItem>()
    var errorMsg = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var showDialog = mutableStateOf(false)
    var inputTitle = mutableStateOf("")
    var inputContent = mutableStateOf("")

    // 검색 쿼리
    var query = mutableStateOf("")

    // 선택된 QNA
    val selectedQna = mutableStateOf<QnaItem?>(null)

    private val disposables = CompositeDisposable()

    init {
        Log.d("QnaVM", "초기화: mentorId=${mentorId.value}")
        if (mentorId.value != -1L) {
            loadMentorQna()
        }
    }

    fun loadMentorQna(id: Long = mentorId.value) {
        if (id == -1L) {
            errorMsg.value = "유효하지 않은 멘토 ID입니다."
            items.clear()
            return
        }

        isLoading.value = true
        errorMsg.value = ""

        val d = repository.requestMentorQnaData(id)
            .subscribe({ model ->
                isLoading.value = false
                items.clear()

                if (model.resultCode == "200" && model.result != null) {
                    if (model.result!!.isNotEmpty()) {
                        model.result!!.forEach { q ->
                            items.add(QnaItem(
                                id = q.qnaid?.toLongOrNull() ?: 0L,
                                title = q.qnatitle ?: "제목 없음",
                                content = q.qnacontent ?: "내용 없음",
                                answer = q.qnaanswer ?: "[]"
                            ))
                        }
                    } else {
                        errorMsg.value = "등록된 Q&A가 없습니다."
                    }
                } else {
                    errorMsg.value = model.errorMsg.ifBlank { "Q&A를 불러오지 못했습니다." }
                }

                Log.d("QnaVM", "로딩 완료: ${items.size}개")
            }, { error ->
                isLoading.value = false
                errorMsg.value = "네트워크 오류가 발생했습니다."
                items.clear()
                Log.e("QnaVM", "로딩 에러: ${error.message}")
            })

        disposables.add(d)
    }

    // [질문 등록]
    fun writeQna() {
        Log.d("QnaVM", "글쓰기 시도: ID=${mentorId.value}, Title=${inputTitle.value}")

        if (mentorId.value == -1L) return

        val d = repository.requestAddMentorQna(mentorId.value, inputTitle.value, inputContent.value)
            .subscribe({ model ->
                Log.d("QnaVM", "응답: Code=${model.resultCode}, Msg=${model.errorMsg}")

                if (model.resultCode == "200") {
                    inputTitle.value = ""
                    inputContent.value = ""
                    showDialog.value = false

                    // 목록 갱신
                    loadMentorQna()
                } else {
                    errorMsg.value = model.errorMsg
                }
            }, { error ->
                Log.e("QnaVM", "통신 에러: ${error.message}")
                errorMsg.value = "작성 실패"
            })

        disposables.add(d)
    }

    // [답변(코멘트) 등록]
    fun addAnswer(qnaId: Long, answerContent: String) {
        Log.d("QnaVM", "코멘트 등록 시도: QnaID=$qnaId, Content=$answerContent")

        val d = repository.requestAddAnswer(qnaId, answerContent)
            .subscribe({ model ->
                Log.d("QnaVM", "코멘트 응답: Code=${model.resultCode}, Msg=${model.errorMsg}")

                if (model.resultCode == "200") {
                    loadMentorQna()
                } else {
                    errorMsg.value = "코멘트 등록 실패: ${model.errorMsg}"
                }
            }, { error ->
                Log.e("QnaVM", "코멘트 통신 에러: ${error.message}")
                errorMsg.value = "코멘트 등록 실패"
            })

        disposables.add(d)
    }

    // 검색어 변경
    fun onQueryChange(value: String) {
        query.value = value
    }

    // 필터링된 QNA 리스트
    fun filteredQna(): List<QnaItem> {
        val q = query.value.trim()
        if (q.isBlank()) return items

        return items.filter { item ->
            item.title.contains(q, ignoreCase = true) ||
                    item.content.contains(q, ignoreCase = true)
        }
    }

    // QNA 클릭
    fun onQnaClick(qna: QnaItem) {
        Log.d("QnaVM", "onQnaClick: id=${qna.id}, title=${qna.title}")
        selectedQna.value = qna
        Log.d("QnaVM", "selectedQna 설정 완료")
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    data class QnaItem(
        val id: Long,
        val title: String,
        val content: String,
        val answer: String
    )
}