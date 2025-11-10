package com.example.studify.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify.R

data class Gifticon(val name: String, val price: String, val imageResId: Int)

val sampleGifticons = listOf(
    Gifticon("스타벅스 아메리카노", "500포인트", R.drawable.ic_launcher_background),
    Gifticon("투썸플레이스 케이크", "500포인트", R.drawable.ic_launcher_background),
    Gifticon("올리브영 상품권", "500포인트", R.drawable.ic_launcher_background),
    Gifticon("BHC 치킨", "500포인트", R.drawable.ic_launcher_background),
    Gifticon("메가박스 예매권", "500포인트", R.drawable.ic_launcher_background),
    Gifticon("배스킨라빈스 파인트", "500포인트", R.drawable.ic_launcher_background),
    Gifticon("교보문고 상품권", "500포인트", R.drawable.ic_launcher_background),
    Gifticon("편의점 상품권", "500포인트", R.drawable.ic_launcher_background)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun shop() {
    var showPointInfoScreen by remember { mutableStateOf(false) }

    if (showPointInfoScreen) {
        BackHandler { showPointInfoScreen = false }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("포인트 획득 방법") },
                    navigationIcon = {
                        IconButton(onClick = { showPointInfoScreen = false }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로가기")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("포인트는 이렇게 획득할 수 있어요!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("1. 스터디 매칭에 성공하면 500 포인트를 드립니다.")
                Text("2. Q&A 게시판에서 다른 사람의 질문에 답변을 달면 50 포인트를 드립니다.")
                Text("3. 출석 체크를 하면 매일 10 포인트를 드립니다.")
            }
        }

    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = { TopAppBar(title = { Text("상점") }) }
            ) { paddingValues ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item(span = { GridItemSpan(4) }) {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("광고가 들어갈 자리입니다.", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    items(sampleGifticons) { gifticon ->
                        // --- GifticonItem 함수의 내용이 여기로 통합되었습니다 ---
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = gifticon.imageResId),
                                    contentDescription = gifticon.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = gifticon.name,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text(
                                    text = gifticon.price,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        // -------------------------------------------------
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { showPointInfoScreen = true }) {
                    Text("포인트 획득 방법")
                }
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        text = "잔여 포인트: 1,234P",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
