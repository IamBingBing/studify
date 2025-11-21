package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun matchingOptionFast(
    vm: matchingOptionFastVM = hiltViewModel(),
    navController: NavController
) {
    val startTime = vm.startTime
    val endTime = vm.endTime

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("번개 매칭") }
            )
        },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 상단 설명
            Text(
                text = "지금 가능한 시간을 설정하고\n빠르게 매칭을 시작해 보세요.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 시간 설정 카드
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "시간 설정",
                        style = MaterialTheme.typography.titleMedium
                    )

                    // 시작 시간
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "시작할 시간",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TimeInput(
                            state = startTime,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // 종료 시간
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "끝낼 시간",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TimeInput(
                            state = endTime,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 매칭 시작 버튼
            Button(
                onClick = {
                    // TODO: 매칭 시작 로직
                },
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("매칭 시작")
            }
        }
    }
}
