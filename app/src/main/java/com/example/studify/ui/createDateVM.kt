package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.DateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class createDateVM @Inject constructor(
    application: Application,
    private val dateRepository: DateRepository
) : ViewModel() {

    val dateText = mutableStateOf("날짜를 선택해 주세요")   // "yyyy-MM-dd"
    val title = mutableStateOf("")
    val time = mutableStateOf("")                          // "HH:mm"
    val memo = mutableStateOf("")
    val location = mutableStateOf("")

    private val disposables = CompositeDisposable()

    /** 달력에서 선택한 날짜를 세팅하는 함수 */
    fun setDate(year: Int, month: Int, day: Int) {
        dateText.value = "%04d-%02d-%02d".format(year, month, day)
    }

    /** 저장 가능한지 간단히 체크 (제목만 체크) */
    fun canSave(): Boolean {
        return title.value.isNotBlank()
    }

    /** 일정 저장 */
    fun saveSchedule(
        groupId: Int = 1,         // TODO: 나중에 NavArgument로 실제 그룹ID 넘기기
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (!canSave()) {
            onError("일정 제목을 입력해 주세요.")
            return
        }

        // TIME 컬럼에 넣을 문자열 만들기: "yyyy-MM-dd HH:mm:00"
        val datePart = dateText.value           // 예: "2025-11-21"
        val timePart = if (time.value.isBlank()) "00:00" else time.value  // 예: "09:30"
        val fullTime = "$datePart $timePart:00" // 예: "2025-11-21 09:30:00"

        val disposable = dateRepository.updateDate(
            groupId = groupId,
            title = title.value,
            time = fullTime,
            content = memo.value,
            location = location.value
        ).subscribe({ updateModel ->
            if (updateModel.resultCode == "200") {
                onSuccess()
            } else {
                onError(updateModel.errorMsg ?: "일정 저장에 실패했습니다.")
            }
        }, { e ->
            e.printStackTrace()
            onError("서버 통신에 실패했습니다.")
        })

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
