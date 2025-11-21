package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chatlist(
    vm: chatlistVM = hiltViewModel(),
    navController: NavController
) {
    val error = vm.error
    val chatlist = vm.chatlist   // Map / SnapshotStateMap 형식이라고 가정

    // 에러 다이얼로그
    if (!error.value.isNullOrEmpty()) {
        AlertDialog(
            onDismissRequest = { error.value = "" },
            title = { Text("오류") },
            text = { Text(error.value ?: "") },
            confirmButton = {
                TextButton(onClick = { error.value = "" }) {
                    Text("확인")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("채팅방") }
            )
        },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "참여 중인 채팅방",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "대화를 이어가고 싶은 채팅방을 선택하세요.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (chatlist.isEmpty()) {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = "참여 중인 채팅방이 없습니다.\n그룹에 참여하거나 채팅을 시작해 보세요.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = chatlist.entries.toList(),
                        key = { it.key }
                    ) { entry ->
                        ChatListItem(
                            title = entry.value,
                            onClick = {
                                navController.navigate("chatingRoom/${entry.key}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatListItem(
    title: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = BaseModifiers.BaseModifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}
