package com.example.studify.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
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

    Log.d("qnaDetail", "qnaDetailVM 생성 시도")
    val detailVM: qnaDetailVM = hiltViewModel()
    Log.d("qnaDetail", "qnaDetailVM 생성 성공")
    val navBackStackEntry = navController.currentBackStackEntry
    val qnaIdString = navBackStackEntry?.arguments?.getString("qnaid")
    val qnaId = qnaIdString?.toLongOrNull() ?: -1L
    Log.d("qnaDetail", "qnaId: $qnaId (원본: $qnaIdString)")

    LaunchedEffect(qnaId, qnaVM) {
        Log.d("qnaDetail", "LaunchedEffect 시작: qnaId=$qnaId, qnaVM=${qnaVM != null}")

        if (qnaId != -1L && qnaVM != null) {
            val selected = qnaVM.selectedQna.value
            Log.d("qnaDetail", "selectedQna: ${selected?.id}, title: ${selected?.title}")

            if (selected != null && selected.id == qnaId) {
                Log.d("qnaDetail", "selectedQna 사용")
                detailVM.qnaItem.value = selected
            } else {
                Log.d("qnaDetail", "items에서 찾기, items size: ${qnaVM.items.size}")
                detailVM.loadQnaDetail(qnaId, qnaVM.items)
                Log.d("qnaDetail", "loadQnaDetail 완료, qnaItem: ${detailVM.qnaItem.value?.title}")
            }
        } else {
            Log.e("qnaDetail", "qnaId 또는 qnaVM이 null: qnaId=$qnaId, qnaVM=${qnaVM != null}")
        }
    }

    // qnaItem 가져오기
    val qnaItem = detailVM.qnaItem.value
    Log.d("qnaDetail", "qnaItem: ${qnaItem?.title}, id: ${qnaItem?.id}")

    // 코멘트 파싱 (안전하게)
    val comments = remember(qnaItem?.answer) {
        Log.d("qnaDetail", "코멘트 파싱 시작: answer length=${qnaItem?.answer?.length}")
        val answerText = qnaItem?.answer
        if (answerText.isNullOrBlank() || answerText == "[]") {
            Log.d("qnaDetail", "코멘트 없음")
            emptyList()
        } else {
            try {
                val type = object : TypeToken<List<Comment>>() {}.type
                val result = Gson().fromJson<List<Comment>>(answerText, type) ?: emptyList()
                Log.d("qnaDetail", "코멘트 파싱 성공: ${result.size}개")
                result
            } catch (e: Exception) {
                Log.e("qnaDetail", "코멘트 파싱 에러: ${e.message}", e)
                emptyList()
            }
        }
    }

    Log.d("qnaDetail", "UI 렌더링 시작")

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
        }
    ) { innerPadding ->

        if (qnaItem == null) {
            // qnaItem이 null일 때 에러 메시지
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
            //  qnaItem이 있을 때만 표시
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                // ----- Q&A 내용 -----
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // 제목
                    Text(
                        text = qnaItem?.title ?: "제목 없음",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))

                    // 내용
                    Text(
                        text = qnaItem?.content ?: "내용 없음",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(16.dp))

                    // 코멘트 섹션
                    Text(
                        text = "코멘트 (${comments.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    // 코멘트 리스트
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
                }

                // ----- 코멘트 작성 영역 -----
                Surface(
                    tonalElevation = 2.dp,
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
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
                                val itemId = qnaItem?.id
                                if (commentText.isNotBlank() && itemId != null && itemId != -1L) {
                                    detailVM.addAnswer(itemId, commentText)
                                    commentText = ""
                                }
                            },
                            enabled = commentText.isNotBlank() && qnaItem != null
                        ) {
                            Text("등록")
                        }
                    }
                }
            }
        } // if-else 종료
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // 작성자 (나중에 실제 이름으로 매핑 가능)
            Text(
                text = "작성자 ${comment.writer}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(4.dp))

            // 코멘트 내용
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Comment 데이터 클래스 (qnaDetail에서 사용)
data class Comment(val writer: String = "", val content: String = "")