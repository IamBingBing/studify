package com.example.studify.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.ProgressModel
import com.example.studify.data.model.ProgressModel.ProgressResult.Purpose
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

    var mainGoal = mutableStateOf("")
    var personalGoals = mutableStateListOf<Purpose>()
    var progressPercent = mutableStateOf(0f)

    var showMainGoalDialog = mutableStateOf(false)
    var showPersonalGoalDialog = mutableStateOf(false)

    // ✅ 수정: String으로 받기 (Repository도 String 받음)
    val groupId = mutableStateOf(savedStateHandle.get<String>("groupid") ?: "-1")

    private val disposables = CompositeDisposable()

    init {
        // VM 생성 시 ID 확인 로그
        Log.d("ProgressVM", "진입 GroupID: ${groupId.value}")
        if (groupId.value != "-1") {
            loadProgress()
        }
    }

    // ✅ 수정: String 그대로 전달 (Int 변환 불필요!)
    fun loadProgress() {
        if (groupId.value == "-1") return

        val d = repository.getProgress(groupId.value)  // ← String 그대로 전달!
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    val list = model.result!!.purposeList

                    if (!list.isNullOrEmpty()) {
                        // 0번 인덱스: 주요 목표
                        mainGoal.value = list[0].purpose

                        // 1번~끝: 개인 목표
                        personalGoals.clear()
                        if (list.size > 1) {
                            personalGoals.addAll(list.subList(1, list.size))
                        }
                        calculateProgress()
                        Log.d("ProgressVM", "로딩 성공: ${list.size}개 항목")
                    }
                }
            }, { error ->
                Log.e("ProgressVM", "로딩 실패: ${error.message}")
            })
        disposables.add(d)
    }

    // ✅ 수정: String 그대로 전달 (Int 변환 불필요!)
    fun saveProgress() {
        if (groupId.value == "-1") {
            Toast.makeText(application, "그룹 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val allGoals = ArrayList<Purpose>()
        // 주요 목표 추가 (완료 여부는 무관하므로 false)
        allGoals.add(Purpose(purpose = mainGoal.value, complit = false))
        // 개인 목표들 추가
        allGoals.addAll(personalGoals)

        val jsonString = Gson().toJson(allGoals)
        Log.d("ProgressVM", "저장 시도: $jsonString")

        val d = repository.saveProgress(groupId.value, jsonString)  // ← String 그대로 전달!
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    // 저장 성공!
                    Log.d("ProgressVM", "저장 완료")
                    calculateProgress()
                } else {
                    Toast.makeText(application, "저장 에러: ${model.errorMsg}", Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Toast.makeText(application, "통신 실패: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        disposables.add(d)
    }

    fun calculateProgress() {
        if (personalGoals.isEmpty()) {
            progressPercent.value = 0f
        } else {
            val doneCount = personalGoals.count { it.complit }
            val total = personalGoals.size
            progressPercent.value = (doneCount.toFloat() / total.toFloat()) * 100f
        }
    }

    fun addPersonalGoal(text: String) {
        personalGoals.add(Purpose(purpose = text, complit = false))
        saveProgress()
    }

    fun toggleGoal(index: Int) {
        val item = personalGoals[index]
        personalGoals[index] = item.copy(complit = !item.complit)
        saveProgress()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}