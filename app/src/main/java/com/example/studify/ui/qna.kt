package com.example.studify.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify.Tool.BaseModifiers

data class Answer(val author: String, val content: String)
data class Question(
    val title: String,
    val content: String,
    val author: String,
    val answers: MutableList<Answer> = mutableListOf()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun qna() {
    var isWriting by remember { mutableStateOf(false) }
    var selectedQuestion by remember { mutableStateOf<Question?>(null) }

    var newQuestionTitle by remember { mutableStateOf("") }
    var newQuestionContent by remember { mutableStateOf("") }
    var newAnswerContent by remember { mutableStateOf("") }

    val questions = remember {
        mutableStateListOf(
            Question("컴포즈 스크롤", "컴포즈에서 스크롤은 어떻게 구현하나요?", "학생1", mutableListOf(Answer("학생2", "LazyColumn을 사용하면 됩니다."))),
            Question("ViewModel 사용법", "ViewModel 사용법이 궁금합니다.", "학생2")
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        when {
            isWriting -> {
                BackHandler { isWriting = false }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("질문 작성") },
                            navigationIcon = {
                                IconButton(onClick = { isWriting = false }) { 
                                    Icon(Icons.Default.ArrowBack, contentDescription = "취소")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = BaseModifiers.BaseModifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = newQuestionTitle,
                            onValueChange = { newQuestionTitle = it },
                            label = { Text("제목") },
                            modifier = BaseModifiers.BaseModifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            value = newQuestionContent,
                            onValueChange = { newQuestionContent = it },
                            label = { Text("내용") },
                            modifier = BaseModifiers.BaseModifier.fillMaxWidth().weight(1f)
                        )
                        Spacer(Modifier.height(16.dp))
                        Row(modifier = BaseModifiers.BaseModifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = { isWriting = false }) { Text("취소") }
                            Button(onClick = {
                                if (newQuestionTitle.isNotBlank()) {
                                    questions.add(0, Question(newQuestionTitle, newQuestionContent, "나"))
                                    newQuestionTitle = ""
                                    newQuestionContent = ""
                                }
                                isWriting = false
                            }) { Text("게시하기") }
                        }
                    }
                }
            }
            selectedQuestion != null -> {
                BackHandler { selectedQuestion = null }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(selectedQuestion!!.title, maxLines = 1) },
                            navigationIcon = {
                                IconButton(onClick = { selectedQuestion = null }) { 
                                    Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(modifier = BaseModifiers.BaseModifier.fillMaxSize().padding(paddingValues)) {
                        LazyColumn(
                            modifier = BaseModifiers.BaseModifier.weight(1f),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                Text(selectedQuestion!!.content, fontSize = 16.sp)
                                Divider(modifier = BaseModifiers.BaseModifier.padding(vertical = 16.dp))
                                Text("답변", fontWeight = FontWeight.Bold)
                            }
                            items(selectedQuestion!!.answers) { answer ->
                                Column {
                                    Text(answer.author, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    Text(answer.content)
                                }
                            }
                        }
                        Row(modifier = BaseModifiers.BaseModifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = newAnswerContent,
                                onValueChange = { newAnswerContent = it },
                                label = { Text("답변 달기") },
                                modifier = BaseModifiers.BaseModifier.weight(1f)
                            )
                            Spacer(Modifier.width(8.dp))
                            Button(onClick = {
                                if (newAnswerContent.isNotBlank()) {
                                    val currentQuestion = selectedQuestion!!
                                    val updatedAnswers = currentQuestion.answers.toMutableList().apply {
                                        add(Answer("나", newAnswerContent))
                                    }
                                    val updatedQuestion = currentQuestion.copy(answers = updatedAnswers)

                                    val index = questions.indexOf(currentQuestion)
                                    if (index != -1) {
                                        questions[index] = updatedQuestion
                                    }
                                    
                                    selectedQuestion = updatedQuestion
                                    newAnswerContent = ""
                                }
                            }) { Text("게시") }
                        }
                    }
                }
            }
            else -> {
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Q&A") }) },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { isWriting = true }) {
                            Icon(Icons.Default.Add, contentDescription = "질문 추가")
                        }
                    }
                ) { paddingValues ->
                    LazyColumn(
                        modifier = BaseModifiers.BaseModifier.fillMaxSize().padding(paddingValues),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(questions) { question ->
                            Card(
                                modifier = BaseModifiers.BaseModifier
                                    .fillMaxWidth()
                                    .clickable { selectedQuestion = question }
                            ) {
                                Column(BaseModifiers.BaseModifier.padding(16.dp)) {
                                    Text(question.title, fontWeight = FontWeight.Bold)
                                    Text("작성자: ${question.author}", fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
