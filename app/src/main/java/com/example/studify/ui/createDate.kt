package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

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

            // 선택된 날짜 표시
            Text(
                text = "선택한 날짜",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = dateText,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 제목 입력
            OutlinedTextField(
                value = title,
                onValueChange = { vm.title.value = it },
                label = { Text("일정 제목") },
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // 시간 입력
            OutlinedTextField(
                value = time,
                onValueChange = { vm.time.value = it },
                label = { Text("시간 (예: 10:00)") },
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // 메모(상세) 내용
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

            // 저장 버튼
            Button(
                onClick = {
                    vm.saveSchedule(
                        onSuccess = {
                            // 저장 성공 시 이전 화면으로
                            navController.popBackStack()
                        },
                        onError = {
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
