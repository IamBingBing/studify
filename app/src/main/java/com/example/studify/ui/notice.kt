package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.AnnounceModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun notice(
    vm: noticeVM = hiltViewModel(),
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) vm.loadNotices()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val query by vm.query
    val notices = vm.filteredNotices()
    val errorMessage by vm.errorMessage

    val density = LocalDensity.current
    val imeBottomPx = WindowInsets.ime.getBottom(density)
    val imeVisible = imeBottomPx > 0
    val imeBottomDp = with(density) { imeBottomPx.toDp() }

    val searchBarHeight = 72.dp

    Scaffold(
        topBar = { groupNavigation(navController = navController) },
        bottomBar = {
            if (!imeVisible) navigationbar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    items(
                        items = notices,
                        key = { it.announceId ?: it.hashCode().toLong() }
                    ) { noticeItem ->
                        NoticeRow(
                            notice = noticeItem,
                            onClick = {
                                vm.onNoticeClick(noticeItem)
                                noticeItem.announceId?.let { id ->
                                    navController.navigate("noticeDetail/$id")
                                }
                            }
                        )
                    }
                }

                if (notices.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "등록된 공지사항이 없습니다.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Surface(tonalElevation = 2.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .padding(top = 8.dp, bottom = if (imeVisible) 0.dp else 8.dp)
                            .padding(bottom = if (imeVisible) imeBottomDp else 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        textField(
                            value = query,
                            onValueChange = { vm.onQueryChange(it) },
                            placeholder = { Text("검색어 입력") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(56.dp)
                        ) {
                            Text("검색")
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate("writeArticle/${vm.groupId.value}") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
                    .offset(
                        y = if (imeVisible) {
                            -(imeBottomDp + searchBarHeight)
                        } else {
                            -searchBarHeight
                        }
                    ),
                containerColor = Color(0xFF8398CE),
                contentColor = Color.White
            ) {
                Text("+")
            }
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
        Row(verticalAlignment = Alignment.CenterVertically) {
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
