@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.studify.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.studify.R
import com.example.studify.Tool.BaseModifiers
import com.example.studify.data.model.BookModel

// 디자인을 위한 커스텀 색상 정의
val BackgroundColor = Color(0xFFF5F7FA) // 연한 회색 배경
val PointColor = Color(0xFF5B67CA)      // 포인트 컬러 (보라빛 파랑)
val TextDark = Color(0xFF2D3436)        // 진한 회색 텍스트
val TextGray = Color(0xFF636E72)        // 중간 회색 텍스트

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
        modifier = BaseModifiers.BaseModifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = BaseModifiers.BaseModifier.fillMaxSize()
        ) {
            // [상단 헤더 영역]
            Row(
                modifier = BaseModifiers.BaseModifier
                    .fillMaxWidth()
                    // [수정] 위쪽(top) 여백을 48.dp로 늘려 시원하게 만들었습니다.
                    .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = BaseModifiers.BaseModifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기",
                        tint = TextDark
                    )
                }

                Spacer(modifier = BaseModifiers.BaseModifier.width(8.dp))

                Text(
                    text = if (startKeyword.isNotBlank()) "\"$startKeyword\" 관련 추천 도서" else "추천 도서",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // [컨텐츠 영역]
            Box(
                modifier = BaseModifiers.BaseModifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = BaseModifiers.BaseModifier.align(Alignment.Center),
                            color = PointColor
                        )
                    }
                    errorMsg != null -> {
                        Column(
                            modifier = BaseModifiers.BaseModifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "오류가 발생했습니다", fontWeight = FontWeight.Bold, color = TextDark)
                            Spacer(modifier = BaseModifiers.BaseModifier.height(8.dp))
                            Text(text = errorMsg!!, color = Color.Red, fontSize = 14.sp)
                        }
                    }
                    bookList.isEmpty() -> {
                        Text(
                            text = "추천해 드릴 책을 찾지 못했어요 😢",
                            color = TextGray,
                            fontSize = 16.sp,
                            modifier = BaseModifiers.BaseModifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            // [수정] 리스트의 맨 아래쪽 여백(bottom)을 60.dp로 넉넉하게 주었습니다.
                            contentPadding = PaddingValues(bottom = 60.dp),
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

// BookItemRow 및 InfoRow는 이전과 동일합니다.
@Composable
fun BookItemRow(book: BookModel.BookInfo) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Card(
        modifier = BaseModifiers.BaseModifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), spotColor = Color.LightGray),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = if (book.image.isNullOrBlank()) R.drawable.logo else book.image,
                contentDescription = "책 표지",
                modifier = BaseModifiers.BaseModifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.1f)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = BaseModifiers.BaseModifier.width(16.dp))

            Column(
                modifier = BaseModifiers.BaseModifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = book.title ?: "제목 없음",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = BaseModifiers.BaseModifier.height(8.dp))

                    InfoRow(
                        icon = Icons.Default.Person,
                        text = book.author ?: "저자 미상"
                    )

                    Spacer(modifier = BaseModifiers.BaseModifier.height(4.dp))

                    InfoRow(
                        icon = Icons.Default.LocationOn,
                        text = book.place ?: "위치 정보 없음",
                        textColor = Color(0xFF2E7D32)
                    )
                }

                val linkText = book.link ?: "정보 없음"
                val isAvailable = linkText != "이용불가"

                Box(
                    modifier = BaseModifiers.BaseModifier
                        .background(
                            color = if (isAvailable) PointColor.copy(alpha = 0.1f) else Color.Red.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .align(Alignment.End)
                ) {

                    Text(
                        text = if (isAvailable) "대출 가능 확인하기 >" else "이용 불가",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isAvailable) PointColor else Color.Red,
                        modifier = Modifier.clickable(onClick = {
                            if(book.detail != "") {
                                uriHandler.openUri(book.detail!!)

                            }
                            else {
                                Toast.makeText(context , "상세페이지가 없습니다.",Toast.LENGTH_SHORT).show()
                            }
                        })
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String, textColor: Color = TextGray) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = BaseModifiers.BaseModifier.size(14.dp),
            tint = textColor.copy(alpha = 0.7f)
        )
        Spacer(modifier = BaseModifiers.BaseModifier.width(4.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}