package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
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
    private val dateRepository: DateRepository
) : ViewModel() {

    private val _groupId = mutableStateOf<Int?>(null)
    val groupId get() = _groupId.value

    // 🔹 서버에서 받아온 전체 일정 (그룹 전체)
    private val _allSchedules = mutableStateOf<List<DateModel.DateResult>>(emptyList())
    val allSchedules get() = _allSchedules.value

    // 🔹 날짜별로 묶은 일정 맵: LocalDate -> 그 날의 일정 리스트
    private val _schedulesByDay = mutableStateOf<Map<LocalDate, List<DateModel.DateResult>>>(emptyMap())
    val schedulesByDay get() = _schedulesByDay.value

    // 🔹 현재 선택된 날짜 (달력에서 클릭한 날짜)
    val selectedDate = mutableStateOf<LocalDate?>(null)

    // 🔹 선택된 날짜의 일정들
    val selectedDateSchedules = mutableStateOf<List<DateModel.DateResult>>(emptyList())

    // 🔹 에러 메시지(있으면 화면에서 보여줄 수 있음)
    val errorMessage = mutableStateOf<String?>(null)

    private val disposables = CompositeDisposable()

    init {
        // 화면 진입 시 자동으로 일정 로딩
        loadAllSchedulesForGroup()
    }

    fun setGroupId(id: Int) {
        _groupId.value = id
    }
    /** 그룹 전체 일정 서버에서 한 번 가져오기 */
    fun loadAllSchedulesForGroup() {
        val realId = groupId ?: return   // groupId 없으면 실행 안함

        val d = dateRepository.requestDateData(realId)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    val list = model.result
                    _allSchedules.value = list

                    val map = list.groupBy { item ->
                        val datePart = item.time.take(10)
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
