/*
package com.example.studify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun qna(vm: qnaVM = hiltViewModel(), navController: NavController) {


    // ✅ 멘토방 전용 - mentorid만 받기
    val navBackStackEntry = navController.currentBackStackEntry
    val mentorIdString = navBackStackEntry?.arguments?.getString("mentorid")
    val id = mentorIdString?.toLongOrNull() ?: -1L

    // 멘토방 전용이므로 항상 true
    val isMentorRoom = true

    // 화면 진입 시 데이터 로드 (멘토방 전용)
    LaunchedEffect(id) {
        if (id != -1L) {
            vm.loadMentorQna(id) // 멘토방 QNA만 로드
        }
    }

    val items = vm.items
    val listState = rememberLazyListState()

    // 새 글이 올라오면 맨 아래로 스크롤
    LaunchedEffect(items.size) {
        if (items.isNotEmpty()) {
            listState.animateScrollToItem(items.size - 1)
        }
    }

    // 입력값 관리
    var inputMessage by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Q&A 채팅") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        },
        modifier = Modifier.imePadding() // 키보드 올라오면 화면 올리기
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {

            // 1. 채팅 리스트
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (items.isEmpty()) {
                    item {
                        Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                            Text("질문을 남겨보세요!", color = Color.Gray)
                        }
                    }
                } else {
                    items(items) { item ->
                        ChatBubble(item = item, vm = vm)
                    }
                }
            }

            // 2. 입력창 영역
            Surface(tonalElevation = 2.dp, color = Color.White) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputMessage,
                        onValueChange = { inputMessage = it },
                        placeholder = { Text("질문을 입력하세요") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        maxLines = 3
                    )

                    Spacer(Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (inputMessage.isNotBlank()) {
                                vm.inputTitle.value = "질문" // 제목은 고정
                                vm.inputContent.value = inputMessage
                                vm.writeQna() // 전송

                                inputMessage = ""
                                focusManager.clearFocus()
                            }
                        },
                        enabled = inputMessage.isNotBlank(),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "전송")
                    }
                }
            }
        }
    }
}

// [통합 컴포넌트] 질문과 답변을 모두 처리
@Composable
fun ChatBubble(
    item: qnaVM.QnaItem,
    vm: qnaVM
) {
    var showAnswerInput by remember { mutableStateOf(false) }
    var answerText by remember { mutableStateOf("") }

    // ✅ 안전한 답변 파싱
    data class Answer(val writer: String = "", val content: String = "")

    val answers = remember(item.id, item.answer) {
        if (item.answer.isBlank() || item.answer == "[]") {
            emptyList<Answer>()
        } else {
            try {
                val type = object : TypeToken<List<Answer>>() {}.type
                val result = Gson().fromJson<List<Answer>>(item.answer, type)
                result?.filter { it.content.isNotBlank() } ?: emptyList()
            } catch (e: Exception) {
                android.util.Log.e("ChatBubble", "파싱 에러: ${e.message}, answer=${item.answer}")
                emptyList()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End // 내 질문은 오른쪽 정렬
    ) {
        // 1. 질문 말풍선 (나)
        Surface(
            shape = RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp),
            color = Color(0xFF4A80F5),
            modifier = Modifier
                .clickable { showAnswerInput = !showAnswerInput } // 클릭 시 답변창 토글
                .padding(bottom = 4.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = item.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.7f))
                Text(text = item.content, color = Color.White, fontSize = 16.sp)
            }
        }

        // 2. 답변 말풍선들 (상대)
        if (answers.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                answers.forEach { answer ->
                    Surface(
                        shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp),
                        color = Color.White,
                        shadowElevation = 1.dp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = answer.content,
                            modifier = Modifier.padding(12.dp),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // 3. 답변 입력창 (클릭 시 보임)
        if (showAnswerInput) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = answerText,
                    onValueChange = { answerText = it },
                    placeholder = { Text("답변 달기...") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Button(
                    onClick = {
                        if (answerText.isNotBlank()) {
                            vm.addAnswer(item.id, answerText)
                            answerText = ""
                            showAnswerInput = false
                        }
                    },
                    modifier = Modifier.height(40.dp)
                ) {
                    Text("등록")
                }
            }
        }
    }
}
*/