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

    var groupName = mutableStateOf("멘토링 스터디 그룹")

    // 화면 상단에 표시할 텍스트
    var mentorCanTeach = mutableStateOf("")
    var menteeWants = mutableStateOf("")

    // 멤버 리스트 (mentor.kt에 정의된 클래스 사용)
    var mentorList = mutableStateListOf<MentorInfo>()
    var menteeList = mutableStateListOf<MenteeInfo>()
    var recentQna = mutableStateOf<List<QnaModel.QnaResult>>(emptyList())
    var currentTab = mutableStateOf(0)
    var mentorError = mutableStateOf("")
    var currentMentorId = mutableStateOf(-1L)

    private val compositeDisposable = CompositeDisposable()

    init {
        // SavedStateHandle에서 mentorid 받아오기
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

                    groupName.value = "멘토링 스터디 그룹"

                    // MENTORID를 currentMentorId에 저장
                    r.mentorid?.let {
                        currentMentorId.value = it
                    }

                    // [수정] TEACH와 LEARN 값에 따라 화면 표시 내용 결정
                    val teach = r.teach
                    val learn = r.learn

                    if (learn == "없음" || learn.isBlank()) {
                        // Case 1: 일반 멘토링 (나는 가르치기만 함)
                        mentorCanTeach.value = teach
                        menteeWants.value = r.qna
                    } else {
                        // Case 2: 지식 교환 (서로 가르침)
                        mentorCanTeach.value = teach
                        menteeWants.value = learn // 멘티가 나에게 가르쳐주는 과목
                    }

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

    // 최근 Q&A 2개 가져오기 (groupVM의 requestNotice 패턴)
    fun requestRecentQna(mentorId: Long = currentMentorId.value) {
        if (mentorId == -1L) {
            recentQna.value = emptyList()
            return
        }

        val d = mentorRepository.requestMentorQnaData(mentorId)
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    // 최근 2개만 가져오기
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