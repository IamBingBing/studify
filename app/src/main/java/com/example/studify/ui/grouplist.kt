package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun grouplist(
    vm: grouplistVM = hiltViewModel(),
    navController: NavController
) {
    val error = vm.error
    val groupList = vm.grouplist   // 이미 “참여 중인 그룹만” 들어있다고 가정

    // 에러 메시지
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
                title = { Text("참여 그룹") }
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
                text = "참여 중인 그룹",
                style = MaterialTheme.typography.titleLarge
            )

            // 참여 그룹이 없을 때
            if (groupList.isEmpty()) {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = "참여 중인 그룹이 없습니다.\n그룹에 참여해 보세요!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                // 참여 중인 그룹 리스트
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = groupList.entries.toList(),
                        key = { it.key }
                    ) { entry ->
                        GroupListItem(
                            name = entry.value,
                            onClick = {
                                navController.navigate("groupHome/${entry.key}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupListItem(
    name: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}
