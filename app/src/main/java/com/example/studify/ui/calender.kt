package com.example.studify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun calender(
    vm: calenderVM = hiltViewModel(),
    navController: NavController
) {
    // VM에서 상태 읽기
    val schedulesByDay = vm.schedulesByDay                   // Map<LocalDate, List<DateResult>>
    val selectedDate by vm.selectedDate                      // LocalDate?
    val selectedDateSchedules by vm.selectedDateSchedules
    val errorMessage by vm.errorMessage

    val groupId = vm.groupId.value

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                vm.loadAllSchedulesForGroup()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


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

    var showDetailDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { groupNavigation(navController = navController) },
        bottomBar = { navigationbar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createDate/${vm.groupId.value}") },
                modifier = Modifier.padding(8.dp),
                containerColor = Color(0xFF8398CE),
                contentColor = Color.White
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

            // 에러 메시지 표시
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
                monthHeader = { /* 위에서 MonthHeader 사용하니까 여기선 비워둠 */ }
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
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = when {
                    !isFromThisMonth -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

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
        shape = RoundedCornerShape(22.dp),
        containerColor = MaterialTheme.colorScheme.surface, // ✅ 팝업 배경 단색
        titleContentColor = Color(0xFF607ABD),       // ✅ 제목 글씨색
        textContentColor = Color.DarkGray,     // ✅ 내용 전체 기본 글씨색
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("닫기")
            }
        },
        title = {
            Column {
                Text(
                    text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },

        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),   // ✅ 전체 균형 여백
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.2.dp,
                    color = Color(0xFFDCE5FC)
                )

                Spacer(Modifier.height(6.dp))

                schedules.forEachIndexed { index, item ->

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // ✅ 제목
                        Text(
                            text = item.title.ifBlank { "제목 없음" },
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF324167),
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(4.dp))

                        // ✅ 시간
                        Text(
                            text = "▸ 시간 : ${item.time}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // ✅ 장소
                        if (item.location.isNotBlank()) {
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "▸ 장소 : ${item.location}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // ✅ 내용
                        if (item.content.isNotBlank()) {
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "▸ 내용 :  ${item.content}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    )
}


