package com.example.studify.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.QnaModel
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

    var groupName = mutableStateOf("")
    var iWillTeach = mutableStateOf("")
    var iWillLearn = mutableStateOf("")
    var mentorList = mutableStateListOf<MentorInfo>()
    var menteeList = mutableStateListOf<MenteeInfo>()
    var recentQna = mutableStateOf<List<QnaModel.QnaResult>>(emptyList())
    var currentTab = mutableStateOf(0)
    var mentorError = mutableStateOf("")
    var currentMentorId = mutableStateOf(-1L)

    private val sharedPreferences =
        application.getSharedPreferences("studify_prefs", Application.MODE_PRIVATE)
    private val currentUserId = sharedPreferences.getLong("userid", -1L)

    private val compositeDisposable = CompositeDisposable()

    init {

        val mentorIdString = savedStateHandle.get<String>("mentorid")
        currentMentorId.value = mentorIdString?.toLongOrNull() ?: -1L

        loadMentorData()
        requestRecentQna()
    }

    fun loadMentorData(mentorId: Long = currentMentorId.value) {
        if (mentorId == -1L) {
            mentorError.value = "유효하지 않은 멘토 ID입니다."
            return
        }

        val d = mentorRepository.requestMentorData(mentorId)
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    val r = model.result!!

                    r.mentorid?.let {
                        currentMentorId.value = it
                    }

                    val teach = r.teach?.trim().orEmpty()
                    val learn = r.learn?.trim().orEmpty()

                    groupName.value = if (learn.isNotBlank() && teach.isNotBlank()) {
                        "${learn}-${teach} 방"
                    } else if (teach.isNotBlank()) {
                        "${teach} 방"
                    } else {
                        ""
                    }

                    // 현재 사용자가 멘토인지 멘티인지 확인
                    val iAmMentor = (currentUserId == r.mentorId)

                    Log.d("mentorVM", "현재 사용자 ID: $currentUserId")
                    Log.d("mentorVM", "멘토 ID: ${r.mentorId}, 멘티 ID: ${r.mentiId}")
                    Log.d("mentorVM", "나는 멘토? $iAmMentor")
                    Log.d("mentorVM", "TEACH=$teach, LEARN=$learn")
                    Log.d("mentorVM", "TITLE=${groupName.value}")

                    if (learn == "없음" || learn.isBlank()) {
                        // Case 1: 일반 멘토링 (LEARN이 없음)
                        if (iAmMentor) {
                            iWillTeach.value = teach
                            iWillLearn.value = r.qna
                        } else {
                            iWillTeach.value = r.qna
                            iWillLearn.value = teach
                        }
                    } else {
                        // Case 2: 지식 교환 (LEARN 있음)
                        if (iAmMentor) {
                            iWillTeach.value = teach
                            iWillLearn.value = learn
                        } else {
                            iWillTeach.value = learn
                            iWillLearn.value = teach
                        }
                    }

                    Log.d("mentorVM", "내가 알려줄 것: ${iWillTeach.value}")
                    Log.d("mentorVM", "내가 배울 것: ${iWillLearn.value}")

                    // 멘토 리스트 채우기
                    mentorList.clear()
                    mentorList.add(MentorInfo(name = r.mentor, field = teach))

                    // 멘티 리스트 채우기
                    menteeList.clear()
                    val menteeGoal = if (learn != "없음" && learn.isNotBlank()) learn else r.qna
                    menteeList.add(MenteeInfo(name = r.menti, goal = menteeGoal))

                    mentorError.value = ""
                } else {
                    mentorError.value = model.errorMsg
                }
            }, { error ->
                mentorError.value = error.toString()
            })

        compositeDisposable.add(d)
    }

    // 최근 Q&A 2개 가져오기
    fun requestRecentQna(mentorId: Long = currentMentorId.value) {
        if (mentorId == -1L) {
            recentQna.value = emptyList()
            return
        }

        val d = mentorRepository.requestMentorQnaData(mentorId)
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    recentQna.value = model.result!!.take(2)
                    Log.d("mentorVM", "최근 QNA 로딩: ${recentQna.value.size}개")
                } else {
                    recentQna.value = emptyList()
                }
            }, { error ->
                Log.e("mentorVM", "QNA 로딩 에러: ${error.message}")
                recentQna.value = emptyList()
            })

        compositeDisposable.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}