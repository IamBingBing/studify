package com.example.studify.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalDensity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun qna(
    vm: qnaVM = hiltViewModel(),
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                vm.loadMentorQna()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val query by vm.query
    val qnaList = vm.filteredQna()
    val errorMessage by vm.errorMsg

    val density = LocalDensity.current
    val imeVisible = WindowInsets.ime.getBottom(density) > 0

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Q&A") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 2.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (imeVisible) {
                                Modifier.imePadding()
                            } else {
                                Modifier.padding(bottom = 64.dp)
                            }
                        )
                        .padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 8.dp),
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
                        onClick = {},
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .height(56.dp)
                    ) {
                        Text("검색")
                    }
                }
            }
        }
    )
     { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    items(
                        items = qnaList,
                        key = { it.id }
                    ) { qnaItem ->
                        QnaRow(
                            qna = qnaItem,
                            onClick = {
                                Log.d("qna", "=== QNA 클릭 ===")
                                Log.d("qna", "클릭한 항목: id=${qnaItem.id}, title=${qnaItem.title}")
                                try {
                                    vm.onQnaClick(qnaItem)
                                    val route = "qnaDetail/${qnaItem.id}"
                                    navController.navigate(route)
                                } catch (e: Exception) {
                                    Log.e("qna", "클릭 처리 에러: ${e.message}", e)
                                }
                            }
                        )
                    }
                }

                if (qnaList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage.ifBlank { "등록된 Q&A가 없습니다." },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            FloatingActionButton(
                onClick = { vm.showDialog.value = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = if (imeVisible) (-16).dp else (-80).dp)
                    .padding(16.dp)
            ) {
                Text("+")
            }
        }

        if (vm.showDialog.value) {
            AlertDialog(
                onDismissRequest = { vm.showDialog.value = false },
                title = { Text("Q&A 작성") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = vm.inputTitle.value,
                            onValueChange = { vm.inputTitle.value = it },
                            label = { Text("제목") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = vm.inputContent.value,
                            onValueChange = { vm.inputContent.value = it },
                            label = { Text("내용") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 5
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            vm.writeQna()
                            vm.showDialog.value = false
                        }
                    ) { Text("등록") }
                },
                dismissButton = {
                    TextButton(onClick = { vm.showDialog.value = false }) {
                        Text("취소")
                    }
                }
            )
        }
    }
}

@Composable
private fun QnaRow(
    qna: qnaVM.QnaItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = qna.title,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = qna.content.take(50) + if (qna.content.length > 50) "..." else "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(2.dp))

        val commentCount = try {
            val answers = com.google.gson.Gson().fromJson(
                qna.answer,
                com.google.gson.reflect.TypeToken.getParameterized(
                    List::class.java,
                    Map::class.java
                ).type
            ) as? List<*>
            answers?.size ?: 0
        } catch (e: Exception) {
            0
        }

        Text(
            text = "코멘트: $commentCount",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
