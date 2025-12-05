package com.example.studify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import com.example.studify.Tool.Preferences

@Composable
fun chatingRoom(
    vm: chatingRoomVM = hiltViewModel(),
    navController: NavController
) {
    val messages = vm.message.collectAsState()        // vm.message: State<List<Chat>>
    var sendMessage by vm.sendmessage // vm.sendmessage: MutableState<String>
    val myName = Preferences.getLong("USERID")

    Scaffold(
        topBar = {
            ChatTopBar(
                title = "채팅방",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = { navigationbar(navController) }
    ) { padding ->
        Column(
            modifier = BaseModifiers.BaseModifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // 채팅 리스트
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                items(messages.value) { entry ->
                    val isMine = myName == entry.USERID

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalAlignment = if (isMine) {
                            Alignment.End
                        } else {
                            Alignment.Start
                        }
                    ) {
                        // 내 메시지가 아니면 이름 먼저 보여주기
                        if (!isMine) {
                            Text(
                                text = entry.CHATNAME,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }

                        ChatBubble(
                            text = entry.CHAT,
                            isMine = isMine
                        )
                    }
                }
            }

            // 입력창 + 전송 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = sendMessage,
                    onValueChange = { sendMessage = it },
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp),
                    placeholder = { Text("메시지를 입력하세요") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { vm.sendMessage() },
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("전송")
                }
            }
        }
    }
}

@Composable
fun ChatTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 가운데 제목
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f)) // 좌우 균형 맞추기
    }
}


@Composable
private fun ChatBubble(
    text: String,
    isMine: Boolean
) {
    val bubbleColor = if (isMine) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val textColor = if (isMine) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .background(
                color = bubbleColor,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = if (isMine) 0.dp else 16.dp,
                    bottomStart = if (isMine) 16.dp else 0.dp
                )
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = textColor
        )
    }
}
