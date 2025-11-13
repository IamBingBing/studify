package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

data class Notice(
    val id: Int,
    val title: String,
    val isPinned: Boolean = false,
    val date: Long
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun notice(
    notices: List<Notice> = listOf(
        Notice(1, "첫 모임 안내", isPinned = true, date = 1),
        Notice(2, "스터디 규칙 안내", isPinned = false, date = 3),
    ),
    onWriteClick: () -> Unit = {}, navController: NavController
) {
    var query by remember { mutableStateOf("") }

    val filtered = notices.filter { it.title.contains(query, ignoreCase = true) }
    val pinned = filtered.filter { it.isPinned }
    val others = filtered.filter { !it.isPinned }.sortedByDescending { it.date }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = BaseModifiers.BaseTextfill
                .fillMaxSize()
        ) {
            LazyColumn( //화면에 보이는것만 그려줌(스크롤내리면 더보임)
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp), //요소들 간격 자동으로
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                if (pinned.isNotEmpty()) {

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField( //공지 검색창
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("검색어 입력") },
                    singleLine = true,
                    modifier = Modifier.weight(1f) //로우안에 남은공간 차지
                )

                Button(
                    onClick = { /*검색실행*/ },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("검색")
                }
            }
        }

        FloatingActionButton(
            onClick = onWriteClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (-80).dp)
                .padding(16.dp)
        ) {
            Text("+")
        }
    }
}


@Composable
private fun NoticeRow(notice: Notice) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: 공지 상세 */ }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (notice.isPinned) {
                Text(
                    text = "[필독]",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = notice.title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

