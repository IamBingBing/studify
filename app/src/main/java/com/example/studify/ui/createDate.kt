package com.example.studify.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val location by vm.location

    val initialDate = remember(dateText) {
        try { LocalDate.parse(dateText) } catch (e: Exception) { LocalDate.now() }
    }

    var selectedYear by remember { mutableStateOf(initialDate.year) }
    var selectedMonth by remember { mutableStateOf(initialDate.monthValue) }
    var selectedDay by remember { mutableStateOf(initialDate.dayOfMonth) }

    val yearOptions = remember { (initialDate.year - 5..initialDate.year + 5).toList() }
    val monthOptions = remember { (1..12).toList() }
    val daysInMonth = remember(selectedYear, selectedMonth) {
        YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
    }
    val dayOptions = remember(selectedYear, selectedMonth) { (1..daysInMonth).toList() }

    LaunchedEffect(selectedYear, selectedMonth) {
        val maxDay = YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
        if (selectedDay > maxDay) selectedDay = maxDay
    }

    LaunchedEffect(selectedYear, selectedMonth, selectedDay) {
        vm.dateText.value = "%04d-%02d-%02d".format(selectedYear, selectedMonth, selectedDay)
    }

    val initialTime = remember(time) {
        try { LocalTime.parse(time) } catch (e: Exception) { LocalTime.of(9, 0) }
    }

    val timeState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true
    )

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
        val scrollState = rememberScrollState()

        Column(
            modifier = BaseModifiers.BaseModifier
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .imePadding()
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
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
                SimpleDropdown(
                    label = "연도",
                    selected = selectedYear.toString(),
                    options = yearOptions.map { it.toString() },
                    onSelect = { selectedYear = it.toInt() },
                    modifier = Modifier.weight(1f)
                )

                SimpleDropdown(
                    label = "월",
                    selected = selectedMonth.toString(),
                    options = monthOptions.map { it.toString() },
                    onSelect = { selectedMonth = it.toInt() },
                    modifier = Modifier.weight(1f)
                )

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

            OutlinedTextField(
                value = title,
                onValueChange = { vm.title.value = it },
                label = { Text("일정 제목") },
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

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
                        state = timeState,
                        colors = TimePickerDefaults.colors(
                            containerColor = Color(0xFFEEF2FA),
                            selectorColor = Color(0xFFA5B2E0),
                            clockDialColor = Color(0xFFDDE4FF),
                            timeSelectorSelectedContainerColor = Color(0xFFA5B2E0),
                            timeSelectorSelectedContentColor = Color.White,
                            timeSelectorUnselectedContainerColor = Color(0xFFE0E6F8),
                            timeSelectorUnselectedContentColor = Color.Black
                        )
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "선택한 시간: ${vm.time.value}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = location,
                onValueChange = { vm.location.value = it },
                label = { Text("위치") },
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = memo,
                onValueChange = { vm.memo.value = it },
                label = { Text("메모") },
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp),
                maxLines = 5
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    vm.saveSchedule(
                        onSuccess = {
                            Log.d("createDate", "일정 저장 성공")
                            navController.popBackStack()
                        },
                        onError = { msg ->
                            Log.e("createDate", "일정 저장 실패: $msg")
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

            Spacer(Modifier.height(8.dp))
        }
    }
}

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
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFFEAEDFC))
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
