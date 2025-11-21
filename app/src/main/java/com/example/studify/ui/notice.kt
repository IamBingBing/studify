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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.AnnounceModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun notice(
    vm: noticeVM = hiltViewModel(),
    navController: NavController
) {
    val query by vm.query
    // 검색어가 적용된 공지 리스트
    val notices = vm.filteredNotices()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = BaseModifiers.BaseModifier.fillMaxSize()
        ) {
            // 공지 목록 리스트
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(
                    items = notices,
                    key = { it.announceId ?: it.hashCode() }
                ) { noticeItem ->
                    NoticeRow(
                        notice = noticeItem,
                        onClick = {
                            // 클릭된 공지를 VM에 알림
                            vm.onNoticeClick(noticeItem)

                            // 필요하면 상세 페이지로 이동
                            // 나중에 navArgument 쓰면 ID 넘겨서 이동 가능
                            navController.navigate("noticeDetail")
                        }
                    )
                }
            }

            // 검색 영역
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = query,
                    onValueChange = { vm.onQueryChange(it) },
                    placeholder = { Text("검색어 입력") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    // 검색은 query 바뀔 때마다 filteredNotices()에 반영되니까,
                    // 버튼은 있어도 동작은 따로 없어도 됨
                    onClick = { /* 실시간 검색이라 별도 처리 없음 */ },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("검색")
                }
            }
        }

        // 공지 작성 버튼 (지금은 일정 생성 화면으로 연결 중)
        FloatingActionButton(
            onClick = {
                // TODO: 나중에 공지 작성 화면 만들면 그쪽으로 변경
                navController.navigate("createDate")
            },
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
private fun NoticeRow(
    notice: AnnounceModel.AnnounceContent,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 고정 공지면 [필독] 뱃지 표시
            if (notice.isPin == true) {
                Text(
                    text = "[필독] ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = notice.announceName ?: "(제목 없음)",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // 날짜가 있으면 아래에 작게 표시
        notice.announceDate?.let { dateString ->
            Spacer(Modifier.height(2.dp))
            Text(
                text = dateString,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
