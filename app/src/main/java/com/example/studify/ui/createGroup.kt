package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun createGroup(vm: createGroupVM = hiltViewModel(), navController: NavController) {
    var groupName by vm.groupName
    var groupGoal by vm.groupGoal
    var maxMembers by vm.maxMembers
    var intensity by vm.intensity

    // 선택된 목적 1개
    var selectedPurpose by vm.selectedPurpose

    // 목적 리스트
    val purposeList = vm.availablePurpose

    // 선택창 보이기 여부
    val showPicker by vm.showPicker

    Scaffold { innerPadding ->
        Box(
            modifier = BaseModifiers.BaseBoxModifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "그룹방 만들기",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(Modifier.height(16.dp))

                TextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("그룹 이름") },
                    modifier = BaseModifiers.BaseTextfillModifier
                )

                Spacer(Modifier.height(6.dp))

                TextField(
                    value = groupGoal,
                    onValueChange = { groupGoal = it },
                    label = { Text("목표/다짐(텍스트)") },
                    modifier = BaseModifiers.BaseTextfillModifier,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    )
                )

                Spacer(Modifier.height(6.dp))

                TextField(
                    value = maxMembers,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() } && (input.toIntOrNull() ?: 0) <= 30) {
                            maxMembers = input
                        }
                    },
                    label = { Text("최대 정원") },
                    singleLine = true,
                    modifier = BaseModifiers.BaseTextfillModifier
                )

                Spacer(Modifier.height(6.dp))

                PurposeField(
                    selectedPurpose = selectedPurpose,
                    onClick = { vm.openPicker() }
                )

                if (showPicker) {
                    PurposePickerDialog(
                        list = purposeList,
                        selected = selectedPurpose,
                        onSelect = { vm.setPurpose(it) },
                        onDismiss = { vm.closePicker() }
                    )
                }

                Spacer(Modifier.height(6.dp))

                StudyStyleSlider(
                    value = intensity,
                    onChange = { intensity = it }
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        vm.requestCreate(
                            onSuccess = { newGroupId ->
                                // PHP에서 UpdateModel.result 에 넣어준 새 GROUPID 사용
                                navController.navigate("groupHome/$newGroupId")
                            },
                            onError = {
                                // TODO: 스낵바로 바꿔도 됨
                                // 일단은 로그/디버그용으로만 사용한다고 생각하자
                            }
                        )
                    },
                    enabled = vm.canCreate(),
                    modifier = BaseModifiers.BaseBtnModifier
                ) {
                    Text("생성하기")
                }
            }
        }
    }
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
            Column {
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
fun StudyStyleSlider(
    value: Int,
    onChange: (Int) -> Unit
) {
    Column(modifier = BaseModifiers.BaseTextfillModifier) {

        Slider(
            value = value.toFloat(),
            onValueChange = { onChange(it.toInt()) },
            valueRange = 0f..100f,
            modifier = BaseModifiers.BaseTextfillModifier
                .height(56.dp)
                .width(280.dp)
        )

        Spacer(Modifier.height(1.dp))

        Row(
            modifier = BaseModifiers.BaseTextfillModifier
                .height(56.dp)
                .width(280.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("여유로움")
            Text(
                when {
                    value < 20 -> "매우 여유"
                    value < 40 -> "조금 여유"
                    value < 60 -> "보통"
                    value < 80 -> "조금 빡셈"
                    else       -> "매우 빡셈"
                },
                style = MaterialTheme.typography.labelSmall
            )
            Text("빡셈")
        }
    }
}
