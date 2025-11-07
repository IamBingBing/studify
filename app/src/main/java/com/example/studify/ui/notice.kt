package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studify.Tool.BaseModifiers

data class Notice(
    val id: Int,
    val title: String,
    val isPinned: Boolean = false,
    val date: Long // 값이 클수록 최신
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun noticePage(
    notices: List<Notice> = listOf(
        Notice(1, "[필독] 첫 모임 안내", isPinned = true, date = 1),
        Notice(2, "11/15 모임 장소 변경", isPinned = false, date = 3),
        Notice(3, "스터디 규칙 안내", isPinned = false, date = 2),
    ),
    onBackClick: () -> Unit = {},
    onWriteClick: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    // 검색 적용
    val filtered = notices.filter { it.title.contains(query, ignoreCase = true) }

    // 핀 공지 / 일반 공지 분리 + 최신순
    val pinned = filtered.filter { it.isPinned }
    val others = filtered.filter { !it.isPinned }.sortedByDescending { it.date }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("공지사항") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            Column(
                modifier = BaseModifiers.BaseTextfill
                    .fillMaxSize()
            ) {
                // 공지 리스트
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    if (pinned.isNotEmpty()) {
                        item {
                            Text(
                                text = "필수 공지",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                            )
                        }
                        items(pinned) { notice ->
                            NoticeRow(notice)
                        }
                        item {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

                    items(others) { notice ->
                        NoticeRow(notice)
                    }
                }

                // 아래: 검색창
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("공지 검색") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 오른쪽 아래: 글쓰기 버튼
            FloatingActionButton(
                onClick = onWriteClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("+") // 아이콘 나중에 교체해도 됨
            }
        }
    }
}

@Composable
private fun NoticeRow(notice: Notice) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // TODO: 공지 상세 페이지로 이동 연결
            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        if (notice.isPinned) {
            Text(
                text = "[필수]",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = notice.title,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
