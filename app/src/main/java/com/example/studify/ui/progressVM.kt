package com.example.studify.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.ProgressModel
import com.example.studify.data.repository.ProgressRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class progressVM @Inject constructor(
    private val application: Application,
    private val repository: ProgressRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 화면 표시용 데이터
    var mainGoal = mutableStateOf("") // 주요 목표

    // 개인 목표 리스트 (모델의 Purpose 클래스 사용)
    var personalGoals = mutableStateListOf<ProgressModel.ProgressResult.Purpose>()

    var progressPercent = mutableStateOf(0f)

    // 다이얼로그 상태
    var showMainGoalDialog = mutableStateOf(false)
    var showPersonalGoalDialog = mutableStateOf(false)

    // 네비게이션에서 받은 그룹 ID (progress/{groupId} 로 넘겨받아야 함)
    private val groupId: Int = savedStateHandle.get<String>("groupId")?.toIntOrNull() ?: -1
    private val disposables = CompositeDisposable()

    init {
        if (groupId != -1) {
            loadProgress()
        } else {
            // 테스트용이나 에러 처리
            Toast.makeText(application, "그룹 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // [로드] 서버에서 진도표 가져오기
    fun loadProgress() {
        val d = repository.getProgress(groupId)
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    val list = model.result!!.purposeList

                    if (!list.isNullOrEmpty()) {
                        // 0번 인덱스는 '주요 목표'로 사용
                        mainGoal.value = list[0].purpose

                        // 1번부터 끝까지는 '개인 목표'로 사용
                        personalGoals.clear()
                        if (list.size > 1) {
                            personalGoals.addAll(list.subList(1, list.size))
                        }

                        calculateProgress()
                    }
                }
            }, { error ->
                Toast.makeText(application, "로딩 실패: ${error.message}", Toast.LENGTH_SHORT).show()
            })
        disposables.add(d)
    }

    // [저장] 현재 상태를 JSON으로 만들어 서버로 전송
    fun saveProgress() {
        if (groupId == -1) return

        // 1. 주요 목표와 개인 목표를 하나의 리스트로 합침
        val allGoals = ArrayList<ProgressModel.ProgressResult.Purpose>()

        // 0번: 주요 목표 (완료 여부는 false 고정)
        allGoals.add(ProgressModel.ProgressResult.Purpose(purpose = mainGoal.value, complit = false))

        // 1번~: 개인 목표들
        allGoals.addAll(personalGoals)

        // 2. JSON 변환 (Gson)
        val jsonString = Gson().toJson(allGoals)

        // 3. 서버 전송
        val d = repository.saveProgress(groupId, jsonString)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    Toast.makeText(application, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                    calculateProgress()
                } else {
                    Toast.makeText(application, model.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Toast.makeText(application, "저장 실패: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        disposables.add(d)
    }

    // 진도율 계산
    fun calculateProgress() {
        if (personalGoals.isEmpty()) {
            progressPercent.value = 0f
        } else {
            val doneCount = personalGoals.count { it.complit } // complit이 true인 것 개수
            val total = personalGoals.size
            progressPercent.value = (doneCount.toFloat() / total.toFloat()) * 100f
        }
    }

    // 개인 목표 추가
    fun addPersonalGoal(text: String) {
        personalGoals.add(ProgressModel.ProgressResult.Purpose(purpose = text, complit = false))
        saveProgress()
    }

    // 체크박스 토글
    fun toggleGoal(index: Int) {
        val item = personalGoals[index]
        // 데이터 변경 후 저장
        personalGoals[index] = item.copy(complit = !item.complit)
        saveProgress()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}