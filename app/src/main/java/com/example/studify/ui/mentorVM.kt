package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.MentorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class mentorVM @Inject constructor(
    application: Application,
    private val mentorRepository: MentorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var groupName = mutableStateOf("멘토링 스터디 그룹")
    var mentorCanTeach = mutableStateOf("")
    var menteeWants = mutableStateOf("")
    var mentorList = mutableStateListOf<MentorInfo>()
    var menteeList = mutableStateListOf<MenteeInfo>()
    var currentTab = mutableStateOf(0)
    var mentorError = mutableStateOf("")

    private val compositeDisposable = CompositeDisposable()

    init {
        loadMentorData()
    }

    fun loadMentorData() {
        val d = mentorRepository.requestMentorData()
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    val r = model.result!!

                    groupName.value = "멘토링 스터디 그룹"
                    mentorCanTeach.value = r.teach
                    menteeWants.value = r.qna

                    mentorList.clear()
                    mentorList.add(MentorInfo(name = r.mentor, field = r.teach))

                    menteeList.clear()
                    menteeList.add(MenteeInfo(name = r.menti, goal = r.qna))

                    mentorError.value = ""
                } else {
                    mentorError.value = model.errorMsg
                }
            }, { error ->
                mentorError.value = error.toString()
            })

        compositeDisposable.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}