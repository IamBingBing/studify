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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.BookModel

@Composable
fun searchbook(
    vm: searchbookVM = hiltViewModel(),
    navController: NavController,
    startKeyword: String = ""
) {
    val bookList = vm.bookList
    val isLoading by vm.isLoading
    val errorMsg by vm.errorMsg

    LaunchedEffect(Unit) {
        if (startKeyword.isNotEmpty()) {
            vm.searchBooks(startKeyword)
        }
    }

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
                // [수정됨] 키워드가 있으면 "OOO"에 관한 책 추천, 없으면 그냥 "책 추천" 출력
                Text(
                    text = if (startKeyword.isNotBlank()) "\"$startKeyword\"에 관한 책 추천" else "책 추천",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = BaseModifiers.BaseModifier.padding(bottom = 16.dp)
                )

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
                                text = "추천 도서가 없습니다.",
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

// BookItemRow는 기존과 동일하게 유지
@Composable
fun BookItemRow(book: BookModel.BookInfo) {
    Card(
        modifier = BaseModifiers.BaseModifier
            .fillMaxWidth()
            .height(160.dp),
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

            Column(
                modifier = BaseModifiers.BaseModifier.weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = if (book.title.isNullOrBlank()) "제목 없음" else book.title!!,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = BaseModifiers.BaseModifier.height(4.dp))

                Text(
                    text = "저자: ${if (book.author.isNullOrBlank()) "미상" else book.author}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "위치: ${if (book.place.isNullOrBlank()) "정보 없음" else book.place}",
                    fontSize = 13.sp,
                    color = Color(0xFF2E7D32),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

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