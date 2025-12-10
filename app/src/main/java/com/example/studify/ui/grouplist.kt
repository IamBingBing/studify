package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    val groupList = vm.grouplist      // Map<GROUPID, GROUPNAME>
    val mentorlist = vm.mentorlist    // Map<MENTORID, MENTORNAME>

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
                title = {
                    Text(
                        text = "내 스터디 & 멘토링",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ===== 참여 중인 그룹 섹션 =====
            item {
                SectionHeader(
                    title = "참여 중인 그룹",
                    description = "지금 활동 중인 스터디/그룹이에요.",
                    icon = Icons.Default.Add
                )
            }

            if (groupList.isEmpty()) {
                item {
                    EmptySectionCard(
                        text = "참여 중인 그룹이 없습니다.\n새로운 그룹에 참여해 보세요!"
                    )
                }
            } else {
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

            // 섹션 사이 간격
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // ===== 멘토링 섹션 =====
            item {
                SectionHeader(
                    title = "멘토링",
                    description = "멘토·멘티로 참여 중인 방이에요.",
                    icon = Icons.Default.Add
                )
            }

            if (mentorlist.isEmpty()) {
                item {
                    EmptySectionCard(
                        text = "참여 중인 멘토링 방이 없습니다.\n멘토/멘티 매칭으로 시작해 보세요!"
                    )
                }
            } else {
                items(
                    items = mentorlist.entries.toList(),
                    key = { it.key }
                ) { entry ->
                    GroupListItem(
                        name = entry.value,
                        onClick = {
                            navController.navigate("mentor/${entry.key}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptySectionCard(
    text: String
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
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
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFE0E8F5),
            contentColor = Color(0xFF525564)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}
