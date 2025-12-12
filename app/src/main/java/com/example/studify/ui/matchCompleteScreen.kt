package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchCompleteScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("매칭 완료", fontWeight = FontWeight.Bold) }
            )
        },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎉 매칭이 완료됐어요!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "지금 바로 채팅을 시작해보세요.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    navController.navigate("chatlist") {
                        popUpTo("matchComplete") { inclusive = true }
                    }
                },
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("채팅방으로 이동")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate("grouplist") {
                        popUpTo("matchComplete") { inclusive = true }
                    }
                },
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("그룹 목록으로")
            }
        }
    }
}
