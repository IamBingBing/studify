package com.example.studify.ui

import android.widget.CalendarView
import androidx.compose.foundation.clickable
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
import com.example.studify.data.model.DateModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun calender(
    vm: calenderVM = hiltViewModel(),
    navController: NavController
) {
    val year by vm.year
    val month by vm.month
    val day by vm.day
    val schedules = vm.schedules          // List<DateModel.DateResult>

    val formatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) }
    val selectedDateString = vm.selectedDateString()

    // 상세보기용으로 선택된 일정
    var selectedSchedule by remember { mutableStateOf<DateModel.DateResult?>(null) }

    Scaffold { innerPadding ->
        Box(
            modifier = BaseModifiers.BaseBoxModifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {

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
                            // 한 줄에 제목만 보여주고, 클릭하면 상세 다이얼로그 표시
                            Text(
                                text = "• ${item.title}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.clickable {
                                    selectedSchedule = item
                                }
                            )
                        }
                    }
                }
            }

            // 일정 추가 버튼
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

            // 상세 정보 다이얼로그
            if (selectedSchedule != null) {
                val item = selectedSchedule!!
                AlertDialog(
                    onDismissRequest = { selectedSchedule = null },
                    confirmButton = {
                        TextButton(onClick = { selectedSchedule = null }) {
                            Text("닫기")
                        }
                    },
                    title = { Text(text = item.title) },
                    text = {
                        Column {
                            Text("날짜/시간: ${item.time}")
                            Spacer(Modifier.height(4.dp))
                            Text("장소: ${item.location}")
                            Spacer(Modifier.height(4.dp))
                            Text("내용:")
                            Text(item.content)
                        }
                    }
                )
            }
        }
    }
}
