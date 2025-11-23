package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.DateModel
import com.example.studify.data.repository.DateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class calenderVM @Inject constructor(
    application: Application,
    private val dateRepository: DateRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = mutableStateOf(
        savedStateHandle.get<String>("groupid")?.toIntOrNull() ?: 0
    )

    private val _allSchedules = mutableStateOf<List<DateModel.DateResult>>(emptyList())
    val allSchedules get() = _allSchedules.value

    private val _schedulesByDay =
        mutableStateOf<Map<LocalDate, List<DateModel.DateResult>>>(emptyMap())
    val schedulesByDay get() = _schedulesByDay.value
    val selectedDate = mutableStateOf<LocalDate?>(null)
    val selectedDateSchedules = mutableStateOf<List<DateModel.DateResult>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)
    private val disposables = CompositeDisposable()

    init {
        // 화면 진입 시 자동으로 일정 로딩
        loadAllSchedulesForGroup()
    }

    /** 그룹 전체 일정 서버에서 한 번 가져오기 */
    fun loadAllSchedulesForGroup() {
        val realId = groupId.value
        if (realId == 0) {
            errorMessage.value = "유효하지 않은 그룹입니다."
            _allSchedules.value = emptyList()
            _schedulesByDay.value = emptyMap()
            return
        }

        val d = dateRepository.requestDateData(realId)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    val list = model.result
                    _allSchedules.value = list

                    // TIME = "2025-11-23 10:00:00" 이런 형식이라고 가정하고 앞 10자리만 날짜로 사용
                    val map = list.groupBy { item ->
                        val datePart = item.time.take(10)   // "yyyy-MM-dd"
                        LocalDate.parse(datePart)
                    }
                    _schedulesByDay.value = map

                } else {
                    _allSchedules.value = emptyList()
                    _schedulesByDay.value = emptyMap()
                    errorMessage.value = model.errorMsg
                }
            }, { e ->
                e.printStackTrace()
                errorMessage.value = "서버 오류가 발생했습니다."
                _allSchedules.value = emptyList()
                _schedulesByDay.value = emptyMap()
            })

        disposables.add(d)
    }

    /** 날짜 클릭 시 호출 (calender Composable 에서 사용) */
    fun onDayClicked(date: LocalDate) {
        selectedDate.value = date
        val schedules = schedulesByDay[date] ?: emptyList()
        selectedDateSchedules.value = schedules
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
