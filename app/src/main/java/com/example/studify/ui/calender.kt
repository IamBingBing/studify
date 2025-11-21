package com.example.studify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.DateModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun calender(
    vm: calenderVM = hiltViewModel(),
    navController: NavController
) {
    // 🔥 같은 화면에서 groupVM도 같이 가져오기
    val groupVM: groupVM = hiltViewModel()

    // groupVM 에서 현재 그룹 ID 읽기
    val groupId = groupVM.groupId.value

    // groupId가 설정되면 그걸로 일정 로딩
    LaunchedEffect(groupId) {
        if (groupId != null) {
            vm.setGroupId(groupId)
            vm.loadAllSchedulesForGroup()
        }
    }

    // === 여기부터 캘린더 UI ===

    val schedulesByDay = vm.schedulesByDay           // Map<LocalDate, List<DateResult>>
    val selectedDate by vm.selectedDate              // LocalDate?
    val selectedDateSchedules by vm.selectedDateSchedules
    val errorMessage by vm.errorMessage

    // Kizitonwose Calendar state 설정
    val currentMonth = remember { YearMonth.now() }
    val firstMonth = remember { currentMonth.minusMonths(12) }
    val lastMonth = remember { currentMonth.plusMonths(12) }
    val daysOfWeek = remember { daysOfWeek() }

    val calendarState = rememberCalendarState(
        startMonth = firstMonth,
        endMonth = lastMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    // 날짜 클릭 시 다이얼로그 띄울지 여부
    var showDetailDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createDate") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = BaseModifiers.BaseBoxModifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // 에러 메시지
            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // 상단: 현재 월
            MonthHeader(calendarState.firstVisibleMonth)

            Spacer(Modifier.height(8.dp))

            // 요일 헤더
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                daysOfWeek.forEach { dayOfWeek ->
                    Text(
                        text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            // 실제 캘린더
            HorizontalCalendar(
                state = calendarState,
                dayContent = { day ->
                    DayCell(
                        day = day,
                        hasSchedule = schedulesByDay.containsKey(day.date),
                        isSelected = selectedDate == day.date,
                    ) { clickedDate ->
                        vm.onDayClicked(clickedDate)
                        val list = vm.schedulesByDay[clickedDate] ?: emptyList()
                        if (list.isNotEmpty()) {
                            showDetailDialog = true
                        }
                    }
                },
                monthHeader = { /* 위에서 MonthHeader를 따로 쓰고 있어서 여기서는 필요 없음 */ }
            )
        }

        // 선택된 날짜의 일정 상세 다이얼로그
        if (showDetailDialog && selectedDate != null && selectedDateSchedules.isNotEmpty()) {
            ScheduleDetailDialog(
                date = selectedDate!!,
                schedules = selectedDateSchedules,
                onDismiss = { showDetailDialog = false }
            )
        }
    }
}

/** 상단 "2025년 11월" 이런 헤더 */
@Composable
private fun MonthHeader(month: CalendarMonth) {
    val yearMonth = month.yearMonth
    Text(
        text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        textAlign = TextAlign.Center
    )
}

/** 날짜 한 칸 (숫자 + 점 표시) */
@Composable
private fun DayCell(
    day: CalendarDay,
    hasSchedule: Boolean,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit
) {
    val isFromThisMonth = day.position == DayPosition.MonthDate

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .clickable(enabled = isFromThisMonth) {
                onClick(day.date)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 날짜 숫자
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = when {
                    !isFromThisMonth -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            // 일정 있는 날만 점 표시
            if (hasSchedule && isFromThisMonth) {
                Spacer(Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondary
                        )
                )
            }
        }
    }
}

/** 그 날짜에 있는 일정들 전부 보여주는 다이얼로그 */
@Composable
private fun ScheduleDetailDialog(
    date: LocalDate,
    schedules: List<DateModel.DateResult>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("닫기")
            }
        },
        title = {
            Text("${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 일정")
        },
        text = {
            Column {
                schedules.forEach { item ->
                    Text(
                        text = "제목: ${item.title}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text("시간: ${item.time}")
                    if (item.location.isNotBlank()) {
                        Text("장소: ${item.location}")
                    }
                    if (item.content.isNotBlank()) {
                        Text("내용: ${item.content}")
                    }
                    Divider(modifier = Modifier.padding(vertical = 6.dp))
                }
            }
        }
    )
}
