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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun notice(vm:noticeVM = hiltViewModel(), navController: NavController
) {
    val query by vm.query
    val pinned = vm.pinnedNotices()
    val others = vm.otherNotices()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = BaseModifiers.BaseModifier.fillMaxSize()
        ) {
            LazyColumn( //공지목록:화면에 보이는것만 그려줌(스크롤내리면 더보임)
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp), //요소들 간격 자동으로
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                if (pinned.isNotEmpty()) {
                    items(pinned) { notice ->
                        NoticeRow(
                            notice = notice,
                            onClick = {
                                navController.navigate("noticeDetail")
                            }
                        )
                    }
                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                items(others) { notice ->
                    NoticeRow(notice = notice,
                        onClick = {
                            navController.navigate("noticeDetail")
                        }
                    )
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
                    onValueChange = { vm.onQueryChange(it) },
                    placeholder = { Text("검색어 입력") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = { /*검색실행*/ },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("검색")
                }
            }
        }

        //공지 작성 버튼
        FloatingActionButton(
            onClick = {
                //TODO : 공지 작성창으로 이동
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
private fun NoticeRow(notice: Notice,
                      onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick }
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

