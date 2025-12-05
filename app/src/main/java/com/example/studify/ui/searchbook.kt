@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

// [중요] Coil 3.0.0 사용 시 import 경로 확인
import coil3.compose.AsyncImage
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.BookModel

@Composable
fun searchbook(
    vm: searchbookVM = hiltViewModel(),
    navController: NavController
) {
    // 뷰모델 데이터 연결
    val bookList = vm.bookList
    val isLoading by vm.isLoading
    val errorMsg by vm.errorMsg

    // 검색어 입력 상태
    var keyword by remember { mutableStateOf("") }

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
                // 타이틀
                Text(
                    text = "도서 검색",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = BaseModifiers.BaseModifier.padding(bottom = 16.dp)
                )

                // 검색 입력창 영역
                Row(
                    modifier = BaseModifiers.BaseTextfillModifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = BaseModifiers.BaseModifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        value = keyword,
                        onValueChange = { keyword = it },
                        label = { Text("검색어 입력") },
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            // 뷰모델의 검색 함수 호출
                            vm.searchBooks(keyword)
                            keyboardController?.hide()
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("검색")
                    }
                }

                // 결과 리스트 영역
                Box(
                    modifier = BaseModifiers.BaseModifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
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
                                verticalArrangement = Arrangement.spacedBy(12.dp),
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
            .height(160.dp), // 정보가 많아져서 높이를 확보
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // [이미지] Coil 3.0
            AsyncImage(
                model = if (book.image.isNullOrBlank()) R.drawable.logo else book.image,
                contentDescription = "책 표지",
                modifier = BaseModifiers.BaseModifier
                    .width(90.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = BaseModifiers.BaseModifier.width(16.dp))

            // [정보 텍스트]
            Column(
                modifier = BaseModifiers.BaseModifier.weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                // 1. 제목
                Text(
                    text = if (book.title.isNullOrBlank()) "제목 없음" else book.title!!,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = BaseModifiers.BaseModifier.height(4.dp))

                // 2. 저자
                Text(
                    text = "저자: ${if (book.author.isNullOrBlank()) "미상" else book.author}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // 3. 소장 위치 (New)
                Text(
                    text = "위치: ${if (book.place.isNullOrBlank()) "정보 없음" else book.place}",
                    fontSize = 13.sp,
                    color = Color(0xFF2E7D32), // 짙은 초록색
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f)) // 빈 공간 밀어내기

                // 4. 대출 가능 여부 / 링크 (New)
                // 링크 정보가 "이용불가"면 빨간색, 아니면 파란색
                val linkText = if (book.link.isNullOrBlank()) "정보 없음" else book.link!!
                val isAvailable = linkText != "이용불가"

                Text(
                    text = if (isAvailable) "대출가능 여부 확인 >" else "이용불가",
                    fontSize = 12.sp,
                    color = if (isAvailable) Color.Blue else Color.Red,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}