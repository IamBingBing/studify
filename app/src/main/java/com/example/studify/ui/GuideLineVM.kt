package com.example.studify.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.BookModel
import com.example.studify.data.repository.GuideLineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class GuideLineVM @Inject constructor(
    private val application: Application,
    private val repository: GuideLineRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupGoal = savedStateHandle.get<String>("groupGoal") ?: "목표 없음"
    var isLoading = mutableStateOf(false)
    var bookList = mutableStateListOf<BookModel.BookItem>()

    private val disposables = CompositeDisposable()

    init {
        if (groupGoal != "목표 없음") {
            fetchBooks()
        }
    }

    fun fetchBooks() {
        isLoading.value = true
        bookList.clear()

        val d = repository.getRecommendations(groupGoal)
            .subscribe({ model ->
                isLoading.value = false
                if (model.resultCode == "200" && model.result != null) {
                    bookList.addAll(model.result!!)
                } else {
                    Toast.makeText(application, model.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                isLoading.value = false
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}