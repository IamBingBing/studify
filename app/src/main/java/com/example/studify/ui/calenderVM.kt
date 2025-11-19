package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.DateModel
import com.example.studify.data.repository.DateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class calenderVM @Inject constructor(
    application: Application,
    private val dateRepository: DateRepository
) : ViewModel() {

    // 오늘 날짜로 초기화
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val today = Date()
    private val parts = formatter.format(today).split("-")

    val year = mutableStateOf(parts[0].toInt())   // 예: 2025
    val month = mutableStateOf(parts[1].toInt())  // 예: 11
    val day = mutableStateOf(parts[2].toInt())    // 예: 16

    // 전체 일정 리스트 (서버에서 받은 모든 DateResult)
    private val allSchedules = mutableStateListOf<DateModel.DateResult>()

    // 현재 선택된 날짜의 일정 리스트
    val schedules = mutableStateListOf<DateModel.DateResult>()

    private val disposables = CompositeDisposable()

    // (임시) 이 ViewModel이 사용할 그룹 ID
    // 나중에 NavArgument 등으로 실제 그룹ID를 받아서 넣으면 됨.
    private val currentGroupId: Int = 1

    init {
        // 화면 들어오자마자 해당 그룹의 전체 일정 불러오기
        loadAllSchedulesForGroup()
    }

    /** 현재 선택된 날짜를 yyyy-MM-dd 문자열로 반환 */
    fun selectedDateString(): String =
        "%04d-%02d-%02d".format(year.value, month.value, day.value)

    /** 캘린더에서 날짜 선택했을 때 호출 */
    fun onDateSelected(y: Int, mFromCalendarView: Int, d: Int) {
        year.value = y
        month.value = mFromCalendarView + 1   // CalendarView는 0부터 시작이라 +1
        day.value = d

        // 날짜 바뀌면 해당 날짜 일정 다시 필터링
        filterSchedulesForSelectedDate()
    }

    /** 서버에서 그룹 전체 일정 한번 가져오기 */
    private fun loadAllSchedulesForGroup() {
        val disposable = dateRepository.requestDate(currentGroupId)
            .subscribe({ dateModel ->
                allSchedules.clear()
                allSchedules.addAll(dateModel.result)

                // 처음 들어왔을 때: 오늘 날짜 기준으로 한번 필터링
                filterSchedulesForSelectedDate()
            }, { error ->
                // 에러 발생 시: 리스트 비우고, 필요하면 로그/에러 상태 처리
                allSchedules.clear()
                schedules.clear()
                // 여기서 에러 메시지용 state 하나 더 만들어도 됨
            })

        disposables.add(disposable)
    }

    /** allSchedules에서 현재 선택된 날짜에 해당하는 일정만 골라서 schedules에 채우기 */
    private fun filterSchedulesForSelectedDate() {
        val dateKey = selectedDateString()  // 예: "2025-11-19"
        schedules.clear()

        // TIME 이 "yyyy-MM-dd HH:mm:ss" 형태라고 가정하고
        // 앞의 10글자(날짜 부분)만 비교
        val filtered = allSchedules.filter { schedule ->
            schedule.time.take(10) == dateKey
        }

        schedules.addAll(filtered)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
