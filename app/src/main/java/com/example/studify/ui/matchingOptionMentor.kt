package com.example.studify.ui

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
import androidx.compose.material3.Scaffold
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun matchingOptionMentor(
    vm: matchingOptionMentorVM = hiltViewModel(),
    navController: NavController
) {
    var expandedLearn by remember { mutableStateOf(false) }
    var expandedTeach by remember { mutableStateOf(false) }
    var wantLearn by vm.wantlearn
    var wantTeach by vm.wantteach
    var showPicker by remember { mutableStateOf(false)}
    var selectedPurpose by remember { mutableStateOf("") }
    if (vm.matchcomplete.value){
        navController.navigate("grouplist"){

        }
    }
    val subjectOptions = studylist.contents

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("지식 교환 매칭") }
            )
        },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 상단 설명
            Text(
                text = "배우고 싶은 과목과 알려줄 수 있는 과목을 선택하면\n서로 맞는 멘토/멘티를 찾아드려요.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // 배우고 싶은 과목 카드
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
                        text = "배우고 싶은 과목",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Button(
                        onClick = { expandedLearn = true },
                        modifier = BaseModifiers.BaseBtnModifier.fillMaxWidth()
                    ) {
                        Text(wantLearn.ifBlank { "과목을 선택하세요" })
                    }

                    DropdownMenu(
                        expanded = expandedLearn,
                        onDismissRequest = { expandedLearn = false }
                    ) {
                        subjectOptions.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    wantLearn = item
                                    expandedLearn = false
                                }
                            )
                        }
                    }
                }
            }

            // 알려줄 수 있는 과목 카드
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
                        text = "알려줄 수 있는 과목",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Button(
                        onClick = { expandedTeach = true },
                        modifier = BaseModifiers.BaseBtnModifier.fillMaxWidth()
                    ) {
                        Text(wantTeach.ifBlank { "과목을 선택하세요" })
                    }

                    PurposeField(
                        selectedPurpose = wantLearn,
                        onClick = { showPicker = true }
                    )

                    if (showPicker) {
                        PurposePickerDialog(
                            list = studylist.contents,
                            selected = selectedPurpose,
                            onSelect = { expandedTeach = it },
                            onDismiss = { showPicker=false }
                        )
                    }
                }
            }
            """
            // 가능한 날짜 카드
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
                        dayList.forEach { day ->
                            val selected = day in selectedDays
                            FilterChip(
                                selected = selected,
                                onClick = {
                                    selectedDays =
                                        if (selected) selectedDays - day else selectedDays + day
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
                    vm.requesetmatch()
                },
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("매칭 시작")
            }
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
    @Composable
    fun PurposeField(
        selectedPurpose: String?,
        onClick: () -> Unit
    ) {
        Box(
            modifier = BaseModifiers.BaseBoxModifier
                .height(56.dp)
                .width(280.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 12.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = selectedPurpose ?: "# 스터디 목적 선택",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
