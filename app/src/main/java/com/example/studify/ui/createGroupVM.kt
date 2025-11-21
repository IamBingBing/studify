package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class createGroupVM @Inject constructor(
    application: Application,
    private val groupRepository: GroupRepository
) : ViewModel() {

    var groupName = mutableStateOf("")
    var groupGoal = mutableStateOf("")

    var maxMembers = mutableStateOf("")
    var intensity = mutableStateOf(50)

    // 스터디 목적 선택 (단일 선택)
    var selectedPurpose = mutableStateOf<String?>(null)

    // DB에 미리 등록해둔 목적 목록 (서버에서 불러올 예정)
    var availablePurpose = mutableStateListOf("토익", "자격증", "면접", "공무원", "자기계발")

    // Picker 사용 여부
    var showPicker = mutableStateOf(false)

    fun openPicker() {
        showPicker.value = true
    }

    fun closePicker() {
        showPicker.value = false
    }

    fun setPurpose(value: String) {
        selectedPurpose.value = value
    }

    // 생성 가능한지 검사
    fun canCreate(): Boolean {
        val nameOk = groupName.value.isNotBlank()
        val membersOk = (maxMembers.value.toIntOrNull() ?: 0) in 1..30
        val purposeOk = selectedPurpose.value != null

        return nameOk && membersOk && purposeOk
    }

    // 그룹 생성 요청
    fun requestCreate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!canCreate()) {
            onError("필수 항목을 확인해 주세요.")
            return
        }

        val hostId = 1 // 로그인한 사용자 ID (나중에 실제 로그인 데이터에서 가져올 것)

        groupRepository.createGroup(
            groupName = groupName.value,
            maxLength = maxMembers.value.toIntOrNull() ?: 1,
            hashtag = selectedPurpose.value ?: "",
            tendency = intensity.value,
            purpose = groupGoal.value,
            hostUserId = hostId,
        ).subscribe({ result ->
            if (result.resultCode == "200") {
                onSuccess()
            } else {
                onError(result.errorMsg)
            }
        }, { e ->
            e.printStackTrace()
            onError("생성 실패")
        })
    }
}
