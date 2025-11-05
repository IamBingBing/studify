package com.example.studify.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.studify.Tool.BaseModifiers


@Composable
fun createGroup(){
    var groupName by remember {mutableStateOf("")}
    var showPicker by remember { mutableStateOf(false) }
    var available = remember { mutableStateListOf("토익", "정처기", "한국사") }
    var selected = remember { mutableStateListOf<String>() }


    Box(modifier = BaseModifiers.BaseBoxModifier) {
        Column(
            modifier = BaseModifiers.BaseBoxModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = groupName,
                onValueChange = {groupName = it},
                label = { Text("그룹 이름")},
                modifier = BaseModifiers.BaseTextfill
            )

            Spacer(Modifier.height(12.dp))

            Row(modifier = BaseModifiers.BaseTextfill,
                verticalAlignment = Alignment.CenterVertically)
            {
                Button(
                    onClick = { showPicker = true },
                    modifier = BaseModifiers.BaseBtnModifier
                ) { Text("스터디 목적 선택") }

                Spacer(Modifier.width(8.dp))



                if (showPicker) {
                    HashtagPickerDialog(
                        available = available,
                        selected = selected,
                        onDismiss = { showPicker = false }
                    )
                }
            }
        }
    }

}

@Composable
fun HashtagPickerDialog(
    available: SnapshotStateList<String>,
    selected: SnapshotStateList<String>,
    onDismiss: () -> Unit,
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
                    available.forEach { tag ->
                        val checked = tag in selected
                        FilterChip(
                            selected = checked,
                            onClick = {
                                if (checked) selected.remove(tag) else selected.add(tag)
                            },
                            label = { Text("#$tag") },
                            modifier = BaseModifiers.Chip
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