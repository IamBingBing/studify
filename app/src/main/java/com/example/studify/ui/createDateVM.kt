package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.studify.data.repository.DateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class createDateVM @Inject constructor(
    application: Application,
    private val dateRepository: DateRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = mutableStateOf(
        savedStateHandle.get<String>("groupid")?.toLongOrNull() ?: 0L
    )

    val dateText = mutableStateOf("날짜를 선택해 주세요")   // "yyyy-MM-dd"
    val title = mutableStateOf("")
    val time = mutableStateOf("")                          // "HH:mm"
    val memo = mutableStateOf("")
    val location = mutableStateOf("")

    private val disposables = CompositeDisposable()

    /** 달력에서 선택한 날짜를 세팅 */
    fun setDate(year: Int, month: Int, day: Int) {
        dateText.value = "%04d-%02d-%02d".format(year, month, day)
    }

    fun canSave(): Boolean = title.value.isNotBlank()

    /** 일정 저장 */
    fun saveSchedule(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val gid = groupId.value

        if (gid <= 0) {
            onError("유효하지 않은 그룹 ID입니다.")
            return
        }

        if (!canSave()) {
            onError("일정 제목을 입력해 주세요.")
            return
        }

        val datePart = dateText.value
        val timePart = if (time.value.isBlank()) "00:00" else time.value
        val fullTime = "$datePart $timePart:00"

        val disposable = dateRepository.updateDate(
            groupId = gid.toString(),
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
