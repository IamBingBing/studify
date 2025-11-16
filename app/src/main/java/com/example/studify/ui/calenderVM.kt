package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class calenderVM @Inject constructor(
    application: Application,
    private val studifyService: StudifyService
) : ViewModel() {

    // 오늘 날짜로 초기화
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val today = Date()
    private val parts = formatter.format(today).split("-")

    val year = mutableStateOf(parts[0].toInt())   // 예: 2025
    val month = mutableStateOf(parts[1].toInt())  // 예: 11
    val day = mutableStateOf(parts[2].toInt())    // 예: 16

    // 선택된 날짜의 일정 목록
    val schedules = mutableStateListOf<String>()

    init {
        // 화면 들어오자마자 오늘 날짜 일정 불러오기
        loadSchedulesForSelectedDate()
    }

    /** 현재 선택된 날짜를 yyyy-MM-dd 문자열로 반환 */
    fun selectedDateString(): String =
        "%04d-%02d-%02d".format(year.value, month.value, day.value)

    /** 캘린더에서 날짜 선택했을 때 호출할 함수 */
    fun onDateSelected(y: Int, mFromCalendarView: Int, d: Int) {
        // CalendarView 의 month 는 0부터 시작이기 때문에 +1
        year.value = y
        month.value = mFromCalendarView + 1
        day.value = d

        // 날짜 바뀌면 해당 날짜 일정 다시 로드
        loadSchedulesForSelectedDate()
    }

    fun loadSchedulesForSelectedDate() {
        val dateKey = selectedDateString()
        schedules.clear()

        // TODO: 디비에서 일정 가져오기
        when (dateKey) {
            "2025-11-16" -> {
                schedules.add("스터디 10:00 ~ 12:00 (도서관)")
            }
            else -> {
                schedules.add("등록된 일정이 없습니다.")
            }
        }
    }
}
