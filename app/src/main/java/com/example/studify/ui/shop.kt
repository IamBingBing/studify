package com.example.studify.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.R
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun shop(vm: shopVM = hiltViewModel(), navController: NavController) {

    var showPointInfoScreen by remember { mutableStateOf(false) }
    val itemList = vm.items

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
                modifier = BaseModifiers.BaseModifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("포인트는 이렇게 획득할 수 있어요!", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            }
        }

    } else {
        Box(modifier = BaseModifiers.BaseModifier.fillMaxSize()) {
            Scaffold(
                topBar = { TopAppBar(title = { Text("상점") }) },
                bottomBar = { navigationbar(navController = navController) }
            ) { paddingValues ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = BaseModifiers.BaseModifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 상단 광고 자리
                    item(span = { GridItemSpan(4) }) {
                        Card(modifier = BaseModifiers.BaseModifier.fillMaxWidth()) {
                            Box(
                                modifier = BaseModifiers.BaseModifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("광고가 들어갈 자리입니다.", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // 상품 목록
                    items(itemList) { item ->
                        val name  = item.goodName ?: ""
                        val price = item.price ?: 0
                        val goodId = item.goodId ?: 0

                        Card(
                            modifier = BaseModifiers.BaseModifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = BaseModifiers.BaseModifier.padding(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_background),
                                    contentDescription = name,
                                    modifier = BaseModifiers.BaseModifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    text = name,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = BaseModifiers.BaseModifier.padding(top = 4.dp)
                                )

                                Text(
                                    text = "${price}포인트",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )

                                Button(
                                    onClick = {
                                        navController.navigate("productDetail/$goodId")
                                    },
                                    modifier = BaseModifiers.BaseModifier.padding(top = 4.dp)
                                ) {
                                    Text("구매하기")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
