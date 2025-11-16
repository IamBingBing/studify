package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.studify.Tool.BaseModifiers
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun createGroup(vm: createGroupVM= viewModel(), navController: NavController){

    var groupName by vm.groupName
    var groupGoal by vm.groupGoal
    var showPicker by vm.showPicker
    val available = vm.available
    val selected = vm.selected
    var maxMembers by vm.maxMembers
    var intensity by vm.intensity

    Box(modifier = BaseModifiers.BaseBoxModifier) {
        Column(
            modifier = BaseModifiers.BaseModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "그룹방 만들기",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(Modifier.height(16.dp))

            TextField(
                value = groupName,
                onValueChange = {groupName = it},
                label = { Text("그룹 이름")},
                modifier = BaseModifiers.BaseTextfillModifier

            )

            Spacer(Modifier.height(6.dp))

            TextField(
                value = groupGoal,
                onValueChange = { groupGoal = it },
                label = {Text("목표/다짐")},

                modifier = BaseModifiers.BaseTextfillModifier

            )

            Spacer(Modifier.height(6.dp))

            TextField(
                value = maxMembers,
                onValueChange = { input ->
                    if (input.all { it.isDigit() } && (input.toIntOrNull() ?: 0) <= 30) {
                        maxMembers = input
                    }
                },
                label = { Text("최대 정원")},
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier

            )

            Spacer(Modifier.height(6.dp))

            //해시태그 선택필드
            PurposeField(
                selected = selected,
                onOpenPicker = { vm.openPicker() }
            )

            if (showPicker) {
                HashtagPickerDialog(
                    available = available,
                    selected = selected,
                    onDismiss = { vm.closePicker() }
                )
            }

            Spacer(Modifier.height(6.dp))

            StudyStyleSlider(
                value = intensity,
                onChange = { intensity = it }
            )

            Button(
                onClick = {
                    vm.requestCreate(
                        onSuccess = {
                            //TODO:생성성공 시 이동할 화면
                        },
                        onError = {}
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

@Composable
fun PurposeField(
    selected: List<String>,
    onOpenPicker: () -> Unit
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
            .clickable { onOpenPicker() },
        contentAlignment = Alignment.CenterStart

    ) {
        Row(
            modifier = BaseModifiers.BaseModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selected.isEmpty()) "# 스터디 목적"
                else selected.joinToString(" · ") { "#$it" },
                modifier = BaseModifiers.BaseTextfillModifier
                    .weight(1f)
                    .padding(end = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedButton(
                onClick = onOpenPicker,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("선택")
            }
        }
    }
}



@Composable
fun HashtagPickerDialog(
    available: SnapshotStateList<String>, //리스트 상태 저장소, 리스트요소가 변경될때 자동으로 ui 다시 그려줌
    selected: SnapshotStateList<String>,
    onDismiss: () -> Unit, //닫히는 순간에만 이벤트발생시키기 위해. 입력값도 없고 반환값도 없음
) {
    var newTag by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = onDismiss) { Text("완료") } }, //닫힐때나 뒤로가기
        title = { Text("스터디 목적 선택") },
        text = {
            Column(BaseModifiers.DialogCard) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    available.forEach { tag ->        //available 목록에 잇는 태그들을 하나씩 꺼내서 태그라 부르며 처리
                        val checked = tag in selected
                        FilterChip(
                            selected = checked,
                            onClick = {
                                if (checked) selected.remove(tag)
                                else selected.add(tag)
                            },
                            label = {Text("#$tag") }
                        )

                    }
                }

                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = newTag,
                        onValueChange = { newTag = it },
                        label = { Text("새 해시태그") },
                        singleLine = true,
                        modifier = BaseModifiers.BaseTextfillModifier.weight(1f)

                    )
                    TextButton(onClick = {
                        val t = newTag.trim()
                        if (t.isNotEmpty() && t !in available) available.add(t)
                        if (t.isNotEmpty() && t !in selected)  selected.add(t)
                        newTag = ""
                    }) { Text("추가") }
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

    Column(modifier = BaseModifiers.BaseModifier ) {


        Slider(
            value = value.toFloat(),
            onValueChange = {onChange(it.toInt()) },
            valueRange = 0f..100f,

            modifier = BaseModifiers.BaseModifier

                .height(56.dp)
                .width(280.dp)
        )

        Spacer(Modifier.height(1.dp))

        Row(

            modifier = BaseModifiers.BaseModifier
                .height(56.dp)
                .width(280.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("여유로움")
            Text(when {
                value < 20 -> "매우 여유"
                value < 40 -> "조금 여유"
                value < 60 -> "보통"
                value < 80 -> "조금 빡셈"
                else       -> "매우 빡셈"
            }, style = MaterialTheme.typography.labelSmall)
            Text("빡셈")
        }


    }
}