package com.example.studify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers


data class ChatMessage(val sender: String, val message: String, val isMine: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun mentor(vm : mentorVM = viewModel() , navController: NavController) {
    var mentorSubject by remember { mutableStateOf("") }
    var menteeSubject by remember { mutableStateOf("") }
    var newMessage by remember { mutableStateOf("") }
    val chatMessages = remember {
        mutableStateListOf<ChatMessage>()
    }

    Column(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. 상단 고정 영역 ---
        Text(
            text = "멘토/멘티",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        SubjectCard(
            title = "가르쳐 줄 과목",
            value = mentorSubject,
            onValueChange = { mentorSubject = it }
        )
        Spacer(Modifier.height(12.dp))
        SubjectCard(
            title = "배우고 싶은 과목",
            value = menteeSubject,
            onValueChange = { menteeSubject = it }
        )
        Spacer(Modifier.height(24.dp))
        SectionTitle(title = "진도 체크")
        Button(
            onClick = { /* TODO: progress.kt 화면으로 네비게이션 처리 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("진도 체크 페이지로 이동")
        }

        Spacer(Modifier.height(24.dp))

        // --- 2. 채팅 카드 ---
        ChatCard(
            modifier = Modifier.weight(1f), // 남은 공간을 모두 차지하도록 설정
            messages = chatMessages,
            newMessage = newMessage,
            onNewMessageChange = { newMessage = it },
            onSendMessage = {
                if (newMessage.isNotBlank()) {
                    chatMessages.add(ChatMessage("나", newMessage, true))
                    newMessage = "" // 입력창 비우기
                }
            }
        )
    }
}

@Composable
public fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth()
    )
}

@Composable
public fun SubjectCard(title: String, value: String, onValueChange: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("내용을 입력") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
        }
    }
}


/**
 * 채팅 UI 전체를 담는 카드 Composable
 */
@Composable
public fun ChatCard(
    modifier: Modifier = Modifier,
    messages: List<ChatMessage>,
    newMessage: String,
    onNewMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) { // Card 내부를 꽉 채움
            // 채팅창 제목
            Box(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
                SectionTitle(title = "1:1 채팅")
            }

            // 스크롤 가능한 채팅 내역
            Column(
                modifier = Modifier
                    .weight(1f) // 남은 공간을 모두 차지
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                messages.forEach { msg ->
                    ChatMessageBubble(message = msg)
                }
            }

            // 메시지 입력창
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newMessage,
                    onValueChange = onNewMessageChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("메시지를 입력하세요") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                IconButton(onClick = onSendMessage) {
                    Icon(Icons.Default.Send, contentDescription = "전송")
                }
            }
        }
    }
}

@Composable
public fun ChatMessageBubble(message: ChatMessage) {
    val horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = horizontalArrangement
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (message.isMine) Color(0xFFFEE500) else Color.White) // 카카오톡 색상
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(message.message)
        }
    }
}
