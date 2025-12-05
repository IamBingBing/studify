package com.example.studify.ui

import android.app.Application
import android.util.Log // 로그 추가
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.BookModel
import com.example.studify.data.repository.SearchBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class searchbookVM @Inject constructor(
    application: Application,
    private val searchBookRepository: SearchBookRepository
) : ViewModel() {

    val bookList = mutableStateListOf<BookModel.BookInfo>()
    val isLoading = mutableStateOf(false)
    val errorMsg = mutableStateOf<String?>(null)

    private val disposables = CompositeDisposable()

    fun searchBookByGroup(groupId: String) {
        // [로그] 검색 시작
        Log.d("SearchBookVM", "검색 요청 시작. 입력된 GroupID: $groupId")

        if (groupId.isBlank()) {
            Log.e("SearchBookVM", "검색 실패: GroupID가 비어있음")
            return
        }

        isLoading.value = true
        errorMsg.value = null

        val d = searchBookRepository.requestSearchBook(groupId)
            .subscribe({ model ->
                isLoading.value = false
                Log.d("SearchBookVM", "서버 응답 코드: ${model.resultCode}")

                if (model.resultCode == "200") {
                    bookList.clear()
                    model.result?.let { list ->
                        Log.d("SearchBookVM", "검색 성공: ${list.size}권 가져옴")
                        bookList.addAll(list)
                    }
                    if (bookList.isEmpty()) {
                        Log.d("SearchBookVM", "성공했으나 결과 리스트가 비어있음")
                    }
                } else {
                    val msg = model.errorMsg ?: "검색 결과가 없습니다."
                    Log.e("SearchBookVM", "서버 에러 발생: $msg")
                    bookList.clear()
                    errorMsg.value = msg
                }
            }, { e ->
                isLoading.value = false
                Log.e("SearchBookVM", "통신 실패: ${e.message}")
                e.printStackTrace() // 자세한 에러 로그 출력
                errorMsg.value = "통신 에러: ${e.message}"
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}