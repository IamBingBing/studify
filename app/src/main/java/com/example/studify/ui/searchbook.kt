package com.example.studify.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import coil3.compose.AsyncImage
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.BookModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchbook(
    vm: searchbookVM = hiltViewModel(),
    navController: NavController
) {

    val bookList = vm.bookList
    val isLoading by vm.isLoading
    val errorMsg by vm.errorMsg

    var inputId by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = BaseModifiers.BaseModifier.fillMaxSize()
    ) {
        Surface(
            color = Color.White,
            modifier = BaseModifiers.BaseModifier.fillMaxSize()
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "그룹 해시태그 도서 추천",
                    fontSize = 22.sp,
                    modifier = BaseModifiers.BaseModifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = BaseModifiers.BaseTextfillModifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = BaseModifiers.BaseModifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        value = inputId,
                        onValueChange = { inputId = it },
                        label = { Text("그룹 ID 입력 (숫자)") },
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            vm.searchBookByGroup(inputId)
                            keyboardController?.hide()
                        }
                    ) {
                        Text("검색")
                    }
                }

                Box(
                    modifier = BaseModifiers.BaseModifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                ) {
                    when {
                        isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        errorMsg != null -> {
                            Text(
                                text = errorMsg!!,
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        bookList.isEmpty() -> {
                            Text(
                                text = "검색 결과가 없습니다.",
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                        else -> {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = BaseModifiers.BaseModifier.fillMaxSize()
                            ) {
                                items(bookList) { book ->
                                    BookItemRow(book)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookItemRow(book: BookModel.BookInfo) {
    Card(
        modifier = BaseModifiers.BaseModifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = book.image,
                contentDescription = "책 표지",
                modifier = BaseModifiers.BaseModifier
                    .width(80.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = BaseModifiers.BaseModifier.width(16.dp))

            Column(
                modifier = BaseModifiers.BaseModifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.title ?: "제목 없음",
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 2
                )
                Spacer(modifier = BaseModifiers.BaseModifier.height(4.dp))
                Text(
                    text = "저자: ${book.author ?: "미상"}",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    maxLines = 1
                )
                Text(
                    text = "가격: ${book.price ?: "정보 없음"}",
                    fontSize = 14.sp,
                    color = Color(0xFF0066FF)
                )
            }
        }
    }
}