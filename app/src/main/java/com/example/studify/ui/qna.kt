package com.example.studify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun qna(vm: qnaVM = hiltViewModel(), navController: NavController) {

    val navBackStackEntry = navController.currentBackStackEntry
    val groupIdString = navBackStackEntry?.arguments?.getString("groupId")
    val groupId = groupIdString?.toIntOrNull() ?: -1

    LaunchedEffect(groupId) {
        if (groupId != -1) {
            vm.loadGroupQna(groupId)
        }
    }

    val items = vm.items
    val showDialog by vm.showDialog
    val errorMsg by vm.errorMsg

    if (errorMsg.isNotBlank()) {
        AlertDialog(
            onDismissRequest = { vm.errorMsg.value = "" },
            confirmButton = { TextButton(onClick = { vm.errorMsg.value = "" }) { Text("확인") } },
            text = { Text(errorMsg) }
        )
    }

    Scaffold(
        topBar = { groupNavigation(navController = navController) },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->

        Box(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            // 1. 질문 리스트
            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("등록된 질문이 없습니다.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items) { item ->
                        QnaItemCard(item)
                    }
                }
            }

            FloatingActionButton(
                onClick = { vm.showDialog.value = true },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "작성", tint = Color.White)
            }
        }
    }

    if (showDialog) {
        WriteQnaDialog(vm) { vm.showDialog.value = false }
    }
}

@Composable
fun QnaItemCard(item: qnaVM.QnaItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null, tint = Color.Gray
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.content, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(8.dp))
                if (item.answer.length > 5) {
                    Text(text = "답변이 등록되었습니다.", fontSize = 12.sp, color = Color(0xFF4CAF50))
                } else {
                    Text(text = "답변 대기 중...", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun WriteQnaDialog(vm: qnaVM, onDismiss: () -> Unit) {
    var title by vm.inputTitle
    var content by vm.inputContent

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("질문 작성") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("제목") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("내용") }, modifier = Modifier.height(120.dp))
            }
        },
        confirmButton = { Button(onClick = { vm.writeQna() }) { Text("등록") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("취소") } }
    )
}