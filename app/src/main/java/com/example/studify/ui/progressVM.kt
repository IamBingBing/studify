package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class progressVM @Inject constructor(application: Application,studifyService: StudifyService): ViewModel() {
    var mainGoal = mutableStateOf("")
    var personalGoals = mutableStateListOf<String>()
    var personalGoalsDone = mutableStateListOf<Boolean>()
    var progressPercent = mutableStateOf(0f)
    var showMainGoalDialog = mutableStateOf(false)
    var showPersonalGoalDialog = mutableStateOf(false)
}
