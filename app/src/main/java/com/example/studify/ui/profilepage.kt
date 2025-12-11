package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.ProgressModel.ProgressResult.Purpose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profilepage(
    vm: profilepageVM = hiltViewModel(),
    navController: NavController
) {
    val name by vm.name
    val email by vm.email

    val progressPercent by vm.progressPercent
    val personalGoals = vm.personalGoals

    var isReportDialogOpen by remember { mutableStateOf(false) }
    var reportText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("상세 프로필") },
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

        Box(
            modifier = BaseModifiers.BaseBoxModifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 기본 정보
                ProfileInfoRow(label = "이름", value = name)
                ProfileInfoRow(label = "이메일", value = email)

                // 개인 목표 섹션
                PersonalGoalsSection(
                    progressPercent = progressPercent,
                    goals = personalGoals
                )

                Spacer(modifier = Modifier.height(80.dp))
            }

            // 오른쪽 아래 신고 버튼
            Button(
                onClick = { isReportDialogOpen = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("신고")
            }

            // 신고 다이얼로그
            if (isReportDialogOpen) {
                AlertDialog(
                    onDismissRequest = {
                        isReportDialogOpen = false
                    },
                    title = { Text(text = "신고하기") },
                    text = {
                        Column {
                            Text(text = "신고 내용을 작성해 주세요.")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = reportText,
                                onValueChange = { reportText = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("신고 사유를 입력하세요.") },
                                minLines = 3
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                vm.sendReport(reportText)
                                reportText = ""
                                isReportDialogOpen = false
                            }
                        ) {
                            Text("보내기")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                reportText = ""
                                isReportDialogOpen = false
                            }
                        ) {
                            Text("취소")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}

// 이름/이메일 아래에 붙는 개인 목표 UI
@Composable
fun PersonalGoalsSection(
    progressPercent: Float,
    goals: List<Purpose>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Text(
            text = "개인 목표",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 달성률 텍스트 + ProgressBar
        Text(
            text = "달성률: ${progressPercent.toInt()}%",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = (progressPercent / 100f).coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 개인 목표 리스트
        if (goals.isEmpty()) {
            Text(
                text = "등록된 개인 목표가 없습니다.",
                fontSize = 13.sp,
                color = Color.Gray
            )
        } else {
            goals.forEach { goal ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        text = if (goal.complit) "✓" else "•",
                        fontSize = 14.sp,
                        modifier = Modifier.width(18.dp)
                    )
                    Text(
                        text = goal.purpose,
                        fontSize = 14.sp,
                        textDecoration = if (goal.complit)
                            TextDecoration.LineThrough
                        else
                            TextDecoration.None,
                        color = if (goal.complit) Color.Gray else Color.Unspecified
                    )
                }
            }
        }
    }
}
