package com.example.studify.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.ProgressModel
// ProgressModel 내부 클래스 import
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
    val groupId = mutableStateOf(savedStateHandle.get<String>("groupid")?.toIntOrNull() ?: -1)
    private val disposables = CompositeDisposable()

    init {
        if (groupId.value != -1) {
            loadProgress()
        }
    }

    fun loadProgress() {
        val d = repository.getProgress(groupId.value)
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    val list = model.result!!.purposeList

                    if (!list.isNullOrEmpty()) {
                        mainGoal.value = list[0].purpose

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

    fun saveProgress() {
        if (groupId.value == -1) return

        val allGoals = ArrayList<Purpose>()
        allGoals.add(Purpose(purpose = mainGoal.value, complit = false))
        allGoals.addAll(personalGoals)

        val jsonString = Gson().toJson(allGoals)

        val d = repository.saveProgress(groupId.value, jsonString)
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