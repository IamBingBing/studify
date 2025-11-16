package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class createDateVM @Inject constructor(
    application: Application,
    private val studifyService: StudifyService
) : ViewModel() {

    val dateText = mutableStateOf("날짜를 선택해 주세요")
    val title = mutableStateOf("")
    val time = mutableStateOf("")
    val memo = mutableStateOf("")

    /** 달력에서 선택한 날짜를 세팅하는 함수 */
    fun setDate(year: Int, month: Int, day: Int) {
        dateText.value = "%04d-%02d-%02d".format(year, month, day)
    }

    /** 저장 가능한지 간단히 체크 */
    fun canSave(): Boolean {
        return title.value.isNotBlank()
    }

    /** 일정 저장 */
    fun saveSchedule(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (!canSave()) {
            onError("일정 제목을 입력해 주세요.")
            return
        }

        viewModelScope.launch {
            try {
                // TODO:

                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError("일정 저장에 실패했습니다.")
            }
        }
    }
}
