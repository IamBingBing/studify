package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studify.Tool.BaseModifiers
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun progress(vm : progressVM =  hiltViewModel() , navController: NavController) {
    val mainGoal by vm.mainGoal
    val personalGoals = vm.personalGoals
    val personalGoalsDone = vm.personalGoalsDone
    val progressPercent by vm.progressPercent
    var showMainGoalDialog by vm.showMainGoalDialog
    var showPersonalGoalDialog by vm.showPersonalGoalDialog

    Scaffold(
        topBar = { groupNavigation(navController = navController) },
        bottomBar = { navigationbar(navController) }) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseBoxModifier
                .padding(innerPadding)
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("주요 목표", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                        Text(
                            text = "+",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showMainGoalDialog = true }
                                .padding(4.dp)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = if (mainGoal.isBlank()) "아직 설정된 목표가 없습니다." else mainGoal,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("개인 목표", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                        Text(
                            text = "+",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showPersonalGoalDialog = true }
                                .padding(4.dp)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    if (personalGoals.isEmpty()) {
                        Text("아직 등록된 개인 목표가 없습니다.", fontSize = 14.sp)
                    } else {
                        personalGoals.forEachIndexed { index, text ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = personalGoalsDone[index],
                                    onCheckedChange = {
                                        vm.personalGoalsDone[index] = !vm.personalGoalsDone[index]
                                    }
                                )
                                Text(text, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("진도: ${progressPercent.toInt()}%", fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = (progressPercent / 100f).coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        val total = personalGoalsDone.size
                        val done = personalGoalsDone.count { it }
                        vm.progressPercent.value =
                            if (total == 0) 0f else (done.toFloat() / total.toFloat() * 100f)
                    },
                    modifier = BaseModifiers.BaseBtnModifier.fillMaxWidth()
                ) {
                    Text("진도 체크")
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        if (showMainGoalDialog) {
            var text by remember { mutableStateOf(mainGoal) }

            AlertDialog(
                onDismissRequest = { showMainGoalDialog = false },
                title = { Text("주요 목표 설정") },
                text = {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = BaseModifiers.BaseTextfillModifier.fillMaxWidth(),
                        placeholder = { Text("목표를 입력하세요") }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            vm.mainGoal.value = text
                            showMainGoalDialog = false
                        }
                    ) { Text("저장") }
                },
                dismissButton = {
                    TextButton(onClick = { showMainGoalDialog = false }) { Text("취소") }
                }
            )
        }

        if (showPersonalGoalDialog) {
            var text by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showPersonalGoalDialog = false },
                title = { Text("개인 목표 추가") },
                text = {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = BaseModifiers.BaseTextfillModifier.fillMaxWidth(),
                        placeholder = { Text("예: 기출 10문제 풀기") }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (text.isNotBlank()) {
                                vm.personalGoals.add(text)
                                vm.personalGoalsDone.add(false)
                            }
                            showPersonalGoalDialog = false
                        }
                    ) { Text("추가") }
                },
                dismissButton = {
                    TextButton(onClick = { showPersonalGoalDialog = false }) { Text("취소") }
                }
            )
        }
    }
}