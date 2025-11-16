package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class createGroupVM @Inject constructor(application: Application, studifyService: StudifyService) : ViewModel() {
    var groupName = mutableStateOf("")
    var groupGoal = mutableStateOf("")
    var showPicker = mutableStateOf(false)
    var available = mutableStateListOf("토익", "정처기", "한국사")
    var selected = mutableStateListOf<String>()
    var maxMembers = mutableStateOf("")
    var intensity = mutableStateOf(50)


    fun openPicker() {
        loadTagsFromDb() // DB/서버에서 태그 목록 가져옴
        showPicker.value = true
    }

    fun closePicker() {
        showPicker.value = false
    }

    fun loadTagsFromDb() {
        //TODO :
    }

    // 생성 가능한지 검증
    fun canCreate(): Boolean {
        val nameOk = groupName.value.isNotBlank()
        val membersOk = (maxMembers.value.toIntOrNull() ?: 0) in 1..30
        val tagsOk = selected.isNotEmpty()
        return nameOk && membersOk && tagsOk
    }

    // 나중에 백엔드/DB 저장
    fun requestCreate(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (!canCreate()) {
            onError("필수 항목을 확인해 주세요. (그룹 이름 / 정원 / 해시태그)")
            return
        }

        onSuccess()
    }
}


