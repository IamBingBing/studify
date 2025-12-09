package com.example.studify.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.MentorRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class qnaDetailVM @Inject constructor(
    application: Application,
    private val repository: MentorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var qnaItem = mutableStateOf<qnaVM.QnaItem?>(null)
    var errorMessage = mutableStateOf("")
    private val compositeDisposable = CompositeDisposable()

    fun loadQnaDetail(qnaId: Long, allQnaList: List<qnaVM.QnaItem>) {
        Log.d("qnaDetailVM", "loadQnaDetail 시작: qnaId=$qnaId, list size=${allQnaList.size}")
        qnaItem.value = allQnaList.find { it.id == qnaId }
        if (qnaItem.value == null) {
            Log.e("qnaDetailVM", "QNA ID $qnaId 를 찾을 수 없습니다")
            Log.d("qnaDetailVM", "전체 IDs: ${allQnaList.map { it.id }}")
        } else {
            Log.d("qnaDetailVM", "QNA 찾기 성공: title=${qnaItem.value?.title}")
        }
    }

    fun addAnswer(qnaId: Long, content: String) {
        Log.d("qnaDetailVM", "코멘트 등록 시도: qnaId=$qnaId, content=$content")

        val d = repository.requestAddAnswer(qnaId, content)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    Log.d("qnaDetailVM", "코멘트 등록 성공")

                    // 코멘트를 현재 qnaItem에 추가
                    val currentItem = qnaItem.value
                    if (currentItem != null) {
                        val currentAnswers = try {
                            val type = object : TypeToken<List<Comment>>() {}.type
                            Gson().fromJson<List<Comment>>(currentItem.answer, type)
                                ?: emptyList()
                        } catch (e: Exception) {
                            emptyList()
                        }

                        val newComment = Comment(writer = "나", content = content)
                        val updatedAnswers = currentAnswers + newComment
                        val newAnswerJson = Gson().toJson(updatedAnswers)

                        // qnaItem 업데이트
                        qnaItem.value = currentItem.copy(answer = newAnswerJson)
                    }

                    errorMessage.value = ""
                } else {
                    Log.e("qnaDetailVM", "코멘트 등록 실패: ${model.errorMsg}")
                    errorMessage.value = model.errorMsg
                }
            }, { error ->
                Log.e("qnaDetailVM", "코멘트 등록 에러: ${error.message}")
                errorMessage.value = error.message ?: "에러 발생"
            })

        compositeDisposable.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}