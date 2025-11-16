package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import com.example.studify.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class createGroupVM @Inject constructor(application: Application, private val groupRepository : GroupRepository) : ViewModel() {
    var groupName = mutableStateOf("")
    var groupGoal = mutableStateOf("")
    var showPicker = mutableStateOf(false)
    var available = mutableStateListOf("토익", "정처기", "한국사")
    var selected = mutableStateListOf<String>()
    var maxMembers = mutableStateOf("")
    var intensity = mutableStateOf(50)

    var createError = mutableStateOf("")
    var createSuccess = mutableStateOf(false)


    fun openPicker() {
        loadTagsFromDb() // DB/서버에서 태그 목록 가져옴
        showPicker.value = true
    }

    fun closePicker() {
        showPicker.value = false
    }

    fun loadTagsFromDb() {
        //TODO : 해시태그 목록 불러오기
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
        name: String = groupName.value,
        goal: String = groupGoal.value,
        maxLen: Int = maxMembers.value.toIntOrNull() ?: 0,
        tendency: Int = intensity.value,
        hashtags: List<String> = selected.toList()
    ) {
        if (!canCreate()) {
            createError.value = "필수 항목을 확인해 주세요. (그룹 이름 / 정원 / 해시태그)"
            return
        }


    }
}

