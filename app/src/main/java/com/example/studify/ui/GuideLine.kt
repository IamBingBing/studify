package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Guideline(
    vm: GuideLineVM = hiltViewModel(),
    navController: NavController
) {
    val isLoading by vm.isLoading
    val guidelineList = vm.bookList
    val goal = vm.groupGoal

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("AI 학습 가이드") }) },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->

        Column(
            modifier = BaseModifiers.BaseModifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // [1] 상단 제목 영역
            Text(
                text = "목표: $goal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "목표 달성을 위한 AI의 추천 전략입니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(24.dp))

            // [2] 중간 내용 영역 (로딩 or 리스트) - 남은 공간 차지(weight 1f)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                if (isLoading) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(16.dp))
                        Text("AI가 맞춤형 전략을 짜고 있어요...", modifier = Modifier.padding(top = 10.dp))
                    }
                } else {
                    if (guidelineList.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("가이드라인을 가져오지 못했습니다.")
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(guidelineList) { item ->
                                Card(
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(Modifier.padding(20.dp)) {
                                        Text(
                                            text = "💡 ${item.title}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Spacer(Modifier.height(8.dp))
                                        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                                        Spacer(Modifier.height(8.dp))

                                        Text(
                                            text = item.description,
                                            style = MaterialTheme.typography.bodyMedium,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // [3] 하단 버튼 영역 (친구 페이지로 이동)
            Button(
                onClick = {
                    navController.navigate("bookRecommend")
                },
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(56.dp),
                // 로딩 중일 때는 버튼 못 누르게 막기 (선택사항)
                enabled = !isLoading && guidelineList.isNotEmpty()
            ) {
                Text(text = "이 전략에 맞는 책 추천받기", fontSize = 18.sp)
            }
        }
    }
}