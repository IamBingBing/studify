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
    var availablePurpose = mutableStateListOf(
        "토익", "토플", "오픽", "회화",
        "정처기", "정보보안기사", "리눅스마스터", "SQLD",
        "공무원", "임용", "편입",
        "코딩테스트", "자료구조·알고리즘",
        "전공공부", "과제 같이하기", "프로젝트",
        "자기계발", "독서", "자격증 기타"
    )

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

    fun requestCreate(
        onSuccess: (newGroupId: String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!canCreate()) {
            onError("필수 항목을 확인해 주세요.")
            return
        }

        groupRepository.createGroup(
            groupName = groupName.value,
            maxLength = maxMembers.value.toIntOrNull() ?: 1,
            hashtag = selectedPurpose.value ?: "",
            tendency = intensity.value,
            purpose = groupGoal.value
        ).subscribe({ result ->
            if (result.resultCode == "200") {
                // 새로 생성된 그룹 ID (addGroup.php 에서 echo한 값)
                val newGroupId = result.result
                onSuccess(newGroupId)
            } else {
                onError(result.errorMsg.ifBlank { "그룹 생성에 실패했습니다." })
            }
        }, { e ->
            e.printStackTrace()
            onError("서버 통신 중 오류가 발생했습니다.")
        })
    }
}
