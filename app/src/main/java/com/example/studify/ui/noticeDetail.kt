package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun noticeDetail(
    title: String = "공지 제목 ",
    content: String = "여기가 공지 내용",
    date: String = "2025-11-09",
    onBackClick: () -> Unit = {}, navController: NavController
) {
    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = { Text("공지사항") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(8.dp))


            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant
            )


            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )

            Spacer(Modifier.weight(1f))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /* TODO: 수정 기능 */ }) {
                    Text("수정")
                }
                TextButton(onClick = { /* TODO: 삭제 기능 */ }) {
                    Text("삭제", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}