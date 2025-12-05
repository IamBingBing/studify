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
import com.example.studify.Tool.Preferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun matchMenu(
    vm: matchMenuVM = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("매칭 메뉴") }
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

            // 상단 타이틀 / 설명
            Text(
                text = "어떤 방식으로 만날까요?",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "원하는 매칭 타입을 선택해 주세요.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 가운데 매칭 타입 카드 3개
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MatchMenuItem(
                    title = "번개 매칭",
                    description = "지금 바로\n빠르게 만나기",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (!vm.isfastmatch.value){
                            navController.navigate("matchingOptionFast")
                        }
                        else {
                            navController.navigate("MatchingIng")
                        }
                    }
                )

                MatchMenuItem(
                    title = "그룹 매칭",
                    description = "여러 명과\n함께 스터디",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate("matchingOptionGroup"){
                        popUpTo("matchMenu")
                    } }
                )

                MatchMenuItem(
                    title = "지식 교환",
                    description = "멘토/멘티로\n지식 공유",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (!vm.ismentormatch.value){
                            navController.navigate("matchingOptionMentor") {
                                popUpTo("matchMenu")
                            }
                        }
                        else {
                            navController.navigate("MatchingIng")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 하단 방 만들기 버튼
            Button(
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = { navController.navigate("createGroup") }
            ) {
                Text("방 만들기")
            }
        }
    }
}

@Composable
private fun MatchMenuItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.height(160.dp),
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
