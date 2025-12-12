package com.example.studify.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun qnaDetail(
    navController: NavController
) {
    Log.d("qnaDetail", "=== qnaDetail 시작 ===")

    val parentEntry = remember(navController.currentBackStackEntry) {
        Log.d("qnaDetail", "previousBackStackEntry 가져오기 시도")
        val entry = navController.previousBackStackEntry
        Log.d("qnaDetail", "previousBackStackEntry: ${entry?.destination?.route}")
        entry
    }

    val qnaVM: qnaVM? = parentEntry?.let {
        Log.d("qnaDetail", "qnaVM 가져오기 시도")
        hiltViewModel<qnaVM>(it).also { vm ->
            Log.d("qnaDetail", "qnaVM 가져오기 성공, items size: ${vm.items.size}")
        }
    }

    val detailVM: qnaDetailVM = hiltViewModel()
    val navBackStackEntry = navController.currentBackStackEntry
    val qnaIdString = navBackStackEntry?.arguments?.getString("qnaid")
    val qnaId = qnaIdString?.toLongOrNull() ?: -1L

    LaunchedEffect(qnaId, qnaVM) {
        if (qnaId != -1L && qnaVM != null) {
            val selected = qnaVM.selectedQna.value
            if (selected != null && selected.id == qnaId) {
                detailVM.qnaItem.value = selected
            } else {
                detailVM.loadQnaDetail(qnaId, qnaVM.items)
            }
        }
    }

    val qnaItem = detailVM.qnaItem.value

    val comments = remember(qnaItem?.answer) {
        val answerText = qnaItem?.answer
        if (answerText.isNullOrBlank() || answerText == "[]") {
            emptyList()
        } else {
            try {
                val type = object : TypeToken<List<Comment>>() {}.type
                Gson().fromJson<List<Comment>>(answerText, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    var commentText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Q&A 상세") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        },
        bottomBar = {
            if (qnaItem != null) {
                Surface(
                    tonalElevation = 2.dp,
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .imePadding()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = commentText,
                            onValueChange = { commentText = it },
                            placeholder = { Text("코멘트를 입력하세요") },
                            modifier = Modifier.weight(1f),
                            maxLines = 3
                        )

                        Spacer(Modifier.width(8.dp))

                        Button(
                            onClick = {
                                val itemId = qnaItem.id
                                if (commentText.isNotBlank() && itemId != -1L) {
                                    detailVM.addAnswer(itemId, commentText)
                                    commentText = ""
                                }
                            },
                            enabled = commentText.isNotBlank()
                        ) {
                            Text("등록")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (qnaItem == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Q&A를 찾을 수 없습니다.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("뒤로 가기")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Text(
                        text = qnaItem.title.ifBlank { "제목 없음" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = qnaItem.content.ifBlank { "내용 없음" },
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "코멘트 (${comments.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    if (comments.isEmpty()) {
                        Text(
                            text = "아직 코멘트가 없습니다.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    } else {
                        comments.forEach { comment ->
                            CommentItem(comment = comment)
                            Spacer(Modifier.height(8.dp))
                        }
                    }

                    Spacer(Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "작성자 ${comment.writer}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class Comment(val writer: String = "", val content: String = "")
