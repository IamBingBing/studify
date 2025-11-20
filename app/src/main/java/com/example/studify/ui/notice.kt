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
    // 검색어까지 반영된 공지 리스트
    val notices = vm.filteredNotices()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = BaseModifiers.BaseModifier.fillMaxSize()
        ) {
            // 공지 목록
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
                            // TODO: 공지 ID를 넘겨서 상세 화면에서 다시 조회하게 만들고 싶으면
                            // navController.navigate("noticeDetail/${noticeItem.announceId}")
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
                    // 필터링은 query 변경될 때마다 자동으로 적용되니까
                    // 검색 버튼은 눌러도 따로 할 건 없음 (원하면 onClick에서 키보드 내리기 정도 가능)
                    onClick = { /* 별도 동작 없음 - 실시간 검색 */ },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("검색")
                }
            }
        }

        // 공지 작성 버튼
        FloatingActionButton(
            onClick = {
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
            // 고정 공지면 [필독] 뱃지
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

        // 날짜가 있다면 아래에 작게 표시
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
