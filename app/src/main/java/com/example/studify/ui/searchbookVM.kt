package com.example.studify.ui

import android.app.Application
import android.util.Log
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

    fun searchBooks(keyword: String) {
        Log.d("SearchBookVM", "검색 요청 시작. 키워드: $keyword")

        if (keyword.isBlank()) {
            return
        }

        isLoading.value = true
        errorMsg.value = null

        val params = HashMap<String, String>()
        params["HASHTAG"] = keyword

        val d = searchBookRepository.requestSearchBook(params)
            .subscribe({ model ->
                isLoading.value = false
                Log.d("SearchBookVM", "서버 응답 코드: ${model.resultCode}")

                if (model.resultCode == "200") {
                    bookList.clear()
                    model.result?.let { list ->
                        bookList.addAll(list)
                    }
                } else {
                    val msg = model.errorMsg ?: "검색 결과가 없습니다."
                    bookList.clear()
                    errorMsg.value = msg
                }
            }, { e ->
                isLoading.value = false
                e.printStackTrace()
                errorMsg.value = "통신 에러: ${e.message}"
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}