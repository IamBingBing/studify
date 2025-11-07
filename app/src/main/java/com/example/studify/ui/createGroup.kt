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


@Composable
fun createGroup(){

    var groupName by remember {mutableStateOf("")}
    var groupGoal by remember {mutableStateOf("")}
    var showPicker by remember { mutableStateOf(false) }
    var available = remember { mutableStateListOf("토익", "정처기", "한국사") }
    var selected = remember { mutableStateListOf<String>() }
    var maxMembers by remember {mutableStateOf("")}
    var intensity by remember {mutableStateOf(50)}

    Box(modifier = BaseModifiers.BaseBoxModifier) {
        Column(
            modifier = BaseModifiers.BaseBoxModifier,
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
                modifier = BaseModifiers.BaseTextfill
            )

            Spacer(Modifier.height(6.dp))

            TextField(
                value = groupGoal,
                onValueChange = { groupGoal = it },
                label = {Text("목표/다짐")},
                modifier = BaseModifiers.BaseTextfill
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
                modifier = BaseModifiers.BaseTextfill

            )

            Spacer(Modifier.height(6.dp))

            PurposeField(
                selected = selected,
                onOpenPicker = { showPicker = true }
            )

                if (showPicker) {
                    HashtagPickerDialog(
                        available = available,
                        selected = selected,
                        onDismiss = { showPicker = false } //닫히는 순간 showpicker를 false로
                    )
                }

            Spacer(Modifier.height(6.dp))

            StudyStyleSlider(
                value = intensity,
                onChange = { intensity = it }
            )

            Button(
                onClick = {{
                    //생성하기 버튼 눌렀을 때 발생할 이벤트 나중에 넣을것이다
                }},
                modifier = BaseModifiers.BaseTextfill
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
        modifier = BaseModifiers.BaseTextfill
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
            modifier = BaseModifiers.BaseTextfill,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selected.isEmpty()) "# 스터디 목적"
                else selected.joinToString(" · ") { "#$it" },
                modifier = BaseModifiers.BaseTextfill
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
    modifier: Modifier = Modifier
) {
    var newTag by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = onDismiss) { Text("완료") } }, //닫힐때나 뒤로가기
        title = { Text("스터디 목적 선택") },
        text = {
            Column(BaseModifiers.DialogCard.then(modifier)) {

                Row(
                     horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    available.forEach { tag ->        //available 목록에 잇는 태그들을 하나씩 꺼내서 태그라 부르며 처리
                        val checked = tag in selected
                        FilterChip(
                            selected = checked,
                            onClick = {
                                if (checked) selected.remove(tag) else selected.add(tag)
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
                        modifier = BaseModifiers.BaseTextfill.weight(1f)
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
    Column(modifier = BaseModifiers.BaseTextfill ) {

        Slider(
            value = value.toFloat(),
            onValueChange = {onChange(it.toInt()) },
            valueRange = 0f..100f,
            modifier = BaseModifiers.BaseTextfill
                .height(56.dp)
                .width(280.dp)
        )

        Spacer(Modifier.height(1.dp))

        Row(
            modifier = BaseModifiers.BaseTextfill
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