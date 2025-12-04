package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import com.example.studify.Tool.studylist


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun matchingOptionGroup(
    vm: matchingOptionGroupVM = hiltViewModel(),
    navController: NavController
) {
    var purpose by vm.purpose
    val tendency = vm.tendency
    val days = vm.days

    val purposeOptions = studylist.contents
    var showPicker by remember { mutableStateOf(false)}
    var selectedPurpose by remember { mutableStateOf("") }

    Column(
        modifier = BaseModifiers.BaseModifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 상단 제목 / 설명
        Text(
            text = "그룹 매칭 옵션",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "원하는 스터디 목적과 성향, 가능한 요일을 선택해 주세요.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // 목적 선택 카드
        ElevatedCard(
            modifier = BaseModifiers.BaseBoxModifier.fillMaxWidth()
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "목적",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = { showPicker= true },
                    modifier = BaseModifiers.BaseBtnModifier.fillMaxWidth()
                ) {
                    Text(purpose.ifBlank { "목적을 선택하세요" })
                }

                if (showPicker) {
                    PurposePickerDialog(
                        list = studylist.contents,
                        selected = selectedPurpose,
                        onSelect = {
                            purpose = it
                            showPicker=false  },
                        onDismiss = { showPicker=false }
                    )
                }
            }
        }

        // 그룹 성향 카드
        ElevatedCard(
            modifier = BaseModifiers.BaseBoxModifier.fillMaxWidth()
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "그룹 성향",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "왼쪽으로 갈수록 집중 스터디, 오른쪽으로 갈수록 친목 / 라이트한 분위기입니다.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Slider(
                    value = tendency.value,
                    modifier = BaseModifiers.BaseTextfillModifier
                        .height(56.dp)
                        .width(280.dp) ,
                    valueRange = 0f..100f,
                    onValueChange = {tendency.value = it}
                )

                Row(
                    modifier = BaseModifiers.BaseModifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("집중", style = MaterialTheme.typography.bodySmall)
                    Text("친목", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        """
        // 가능한 요일 카드
        ElevatedCard(
            modifier = BaseModifiers.BaseBoxModifier.fillMaxWidth()
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "가능한 요일",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = BaseModifiers.BaseModifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val dayList = listOf("월", "화", "수", "목", "금", "토", "일")
                    dayList.forEach { day ->
                        val selected = days[day] == true
                        FilterChip(
                            selected = selected,
                            onClick = {
                                // days가 mutableStateMap 이라고 가정
                                days[day] = !selected
                            },
                            label = { Text(day) }
                        )
                    }
                }
            }
        }
        """
        Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))

        // 매칭 시작 버튼
        Button(
            onClick = {
                // TODO: 매칭 시작 로직
            },
            enabled = purpose != "",
            modifier = BaseModifiers.BaseBtnModifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("매칭 시작")
        }
    }
    @Composable
    fun PurposePickerDialog(
        list: List<String>,
        selected: String?,
        onSelect: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("스터디 목적 선택") },
            confirmButton = {
                TextButton(onClick = onDismiss) { Text("닫기") }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    list.forEach { item ->
                        val selectedCheck = (selected == item)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onSelect(item) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCheck,
                                onClick = { onSelect(item) }
                            )
                            Text(item)
                        }
                    }
                }
            }
        )
    }
}


