package com.example.studify.ui

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.viewinterop.AndroidView
import com.example.studify.Tool.BaseModifiers
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CalendarTab(
    vm: calenderVM = hiltViewModel(),
    navController: NavController
) {
    val year by vm.year
    val month by vm.month
    val day by vm.day
    val schedules = vm.schedules

    val formatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) }
    val selectedDateString = vm.selectedDateString()

    Box(
        modifier = BaseModifiers.BaseTextfillModifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 위쪽: 달력 + 일정 리스트
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // 1) 안드로이드 기본 캘린더뷰
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                factory = { context ->
                    CalendarView(context)
                },
                update = { calendarView ->
                    val date = formatter.parse(selectedDateString)
                    if (date != null) {
                        calendarView.date = date.time
                    }

                    calendarView.setOnDateChangeListener { _, y, m, d ->
                        vm.onDateSelected(y, m, d)
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            // 2) 선택된 날짜 텍스트
            Text(
                text = "${month}월 ${day}일 일정",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            // 3) 해당 날짜 일정 리스트
            if (schedules.isEmpty()) {
                Text(
                    text = "등록된 일정이 없습니다.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    schedules.forEach { item ->
                        Text(
                            text = "• $item",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // 오른쪽 아래 + 버튼 (일정 추가)
        FloatingActionButton(
            onClick = {
                navController.navigate("createDate")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            Text("+")
        }
    }
}
