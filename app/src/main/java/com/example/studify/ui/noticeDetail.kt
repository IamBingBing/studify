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
fun noticeDetail(
    vm: noticeDetailVM = hiltViewModel(),
    navController: NavController
) {
    val title by vm.title
    val content by vm.content
    val date by vm.date
    val isLoading by vm.isLoading
    val errorMsg by vm.errorMsg

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("공지사항") },
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
                .fillMaxSize()
                .padding(16.dp)
        ) {

            if (isLoading) {
                // 로딩 중일 때
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (errorMsg != null) {
                // 에러 메시지
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMsg ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                // 제목
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(8.dp))

                // 날짜
                if (date.isNotBlank()) {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Divider(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                // 내용
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                )

                Spacer(Modifier.weight(1f))

                // 하단 버튼
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        // TODO: 수정 기능
                    }) {
                        Text("수정")
                    }
                    TextButton(onClick = {
                        vm.deleteNotice(
                            onSuccess = { navController.popBackStack() },
                            onError = { /* 스낵바 등으로 에러 보여주고 싶으면 여기서 */ }
                        )
                    }) {
                        Text("삭제", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
