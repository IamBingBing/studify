package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun createDate(
    vm: createDateVM = hiltViewModel(),
    navController: NavController
) {
    val dateText by vm.dateText
    val title by vm.title
    val time by vm.time
    val memo by vm.memo
    val location by vm.location   // 🔥 위치 상태

    // 날짜 초기값 세팅
    val initialDate = remember(dateText) {
        try {
            LocalDate.parse(dateText)   // "yyyy-MM-dd" 가정
        } catch (e: Exception) {
            LocalDate.now()
        }
    }

    var selectedYear by remember { mutableStateOf(initialDate.year) }
    var selectedMonth by remember { mutableStateOf(initialDate.monthValue) }
    var selectedDay by remember { mutableStateOf(initialDate.dayOfMonth) }

    val yearOptions = remember { (initialDate.year - 5..initialDate.year + 5).toList() }
    val monthOptions = remember { (1..12).toList() }
    val daysInMonth = remember(selectedYear, selectedMonth) {
        YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
    }
    val dayOptions = remember(selectedYear, selectedMonth) {
        (1..daysInMonth).toList()
    }

    // 월/연도 바뀔 때 일자가 말일 넘으면 보정
    LaunchedEffect(selectedYear, selectedMonth) {
        val maxDay = YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
        if (selectedDay > maxDay) {
            selectedDay = maxDay
        }
    }

    // 선택된 날짜를 vm.dateText("yyyy-MM-dd")에 반영
    LaunchedEffect(selectedYear, selectedMonth, selectedDay) {
        vm.dateText.value = "%04d-%02d-%02d".format(selectedYear, selectedMonth, selectedDay)
    }

    // 시간 초기값 세팅
    val initialTime = remember(time) {
        try {
            LocalTime.parse(time)   // "HH:mm" 가정
        } catch (e: Exception) {
            LocalTime.of(9, 0)      // 기본 09:00
        }
    }

    val timeState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true
    )

    // TimeInput 변경 → vm.time(String "HH:mm")에 반영
    LaunchedEffect(timeState.hour, timeState.minute) {
        vm.time.value = "%02d:%02d".format(timeState.hour, timeState.minute)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("일정 생성") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseModifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            // 날짜 선택 영역
            Text(
                text = "날짜 선택",
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 연도 드롭다운
                SimpleDropdown(
                    label = "연도",
                    selected = selectedYear.toString(),
                    options = yearOptions.map { it.toString() },
                    onSelect = { selectedYear = it.toInt() },
                    modifier = Modifier.weight(1f)
                )

                // 월 드롭다운
                SimpleDropdown(
                    label = "월",
                    selected = selectedMonth.toString(),
                    options = monthOptions.map { it.toString() },
                    onSelect = { selectedMonth = it.toInt() },
                    modifier = Modifier.weight(1f)
                )

                // 일 드롭다운
                SimpleDropdown(
                    label = "일",
                    selected = selectedDay.toString(),
                    options = dayOptions.map { it.toString() },
                    onSelect = { selectedDay = it.toInt() },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "선택한 날짜: $dateText",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 제목입력
            OutlinedTextField(
                value = title,
                onValueChange = { vm.title.value = it },
                label = { Text("일정 제목") },
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // 시간선택
            Text(
                text = "시간 선택",
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TimeInput(
                        state = timeState
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "선택한 시간: ${vm.time.value}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 🔥 위치 입력
            OutlinedTextField(
                value = location,
                onValueChange = { vm.location.value = it },
                label = { Text("위치") },
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // 메모 입력
            OutlinedTextField(
                value = memo,
                onValueChange = { vm.memo.value = it },
                label = { Text("메모") },
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                maxLines = 5
            )

            Spacer(Modifier.weight(1f))

            //  저장 버튼
            Button(
                onClick = {
                    vm.saveSchedule(
                        onSuccess = {
                            navController.popBackStack()
                        },
                        onError = {
                            // TODO: 에러 처리 (스낵바 등)
                        }
                    )
                },
                enabled = vm.canSave(),
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("저장")
            }
        }
    }
}

// 공용 드롭다운 컴포저블 (연/월/일용)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdown(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onSelect(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
