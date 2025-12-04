package com.example.studify.ui

import android.app.Application
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
        if (groupId.isBlank()) return

        isLoading.value = true
        errorMsg.value = null

        val d = searchBookRepository.requestSearchBook(groupId)
            .subscribe({ model ->
                isLoading.value = false

                if (model.resultCode == "200") {
                    bookList.clear()
                    model.result?.let { list ->
                        bookList.addAll(list)
                    }
                } else {
                    bookList.clear()
                    errorMsg.value = model.errorMsg ?: "검색 결과가 없습니다."
                }
            }, { e ->
                isLoading.value = false
                errorMsg.value = "통신 에러: ${e.message}"
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}