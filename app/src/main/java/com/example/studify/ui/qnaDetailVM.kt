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

    private val prefs =
        application.getSharedPreferences("studify_prefs", Application.MODE_PRIVATE)

    private fun getMyUserName(): String {
        val name =
            prefs.getString("username", null)
                ?: prefs.getString("userName", null)
                ?: prefs.getString("USERNAME", null)

        return name?.trim().takeUnless { it.isNullOrBlank() } ?: "나"
    }
    fun loadQnaDetail(qnaId: Long, allQnaList: List<qnaVM.QnaItem>) {
        qnaItem.value = allQnaList.find { it.id == qnaId }
    }

    fun addAnswer(qnaId: Long, content: String) {
        val d = repository.requestAddAnswer(qnaId, content)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    val current = qnaItem.value ?: return@subscribe
                    val currentAnswers: List<Comment> = try {
                        val type = object : TypeToken<List<Comment>>() {}.type
                        Gson().fromJson<List<Comment>>(current.answer, type) ?: emptyList()
                    } catch (e: Exception) {
                        emptyList()
                    }

                    val newComment = Comment(
                        writer = getMyUserName(),
                        content = content
                    )

                    val updated = currentAnswers + newComment
                    qnaItem.value = current.copy(answer = Gson().toJson(updated))

                    errorMessage.value = ""
                } else {
                    errorMessage.value = model.errorMsg
                }
            }, { error ->
                Log.e("qnaDetailVM", "addAnswer error=${error.message}")
                errorMessage.value = error.message ?: "에러 발생"
            })

        compositeDisposable.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}