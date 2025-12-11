package com.example.studify.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun writeArticle(
    vm: writeArticleVM = hiltViewModel(),
    navController: NavController
) {
    val title by vm.title
    val content by vm.content
    val isPin by vm.isPin
    val isLoading by vm.isLoading
    val errorMsg by vm.errorMsg

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("공지 작성") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = BaseModifiers.BaseModifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            // 제목
            OutlinedTextField(
                value = title,
                onValueChange = { vm.title.value = it },
                label = { Text("제목") },
                singleLine = true,
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // 내용
            OutlinedTextField(
                value = content,
                onValueChange = { vm.content.value = it },
                label = { Text("내용") },
                modifier = BaseModifiers.BaseTextfillModifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(Modifier.height(12.dp))

            // 상단 고정 여부
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isPin,
                    onCheckedChange = { vm.isPin.value = it }
                )
                Text("상단 고정")
            }

            if (!errorMsg.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = errorMsg ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    vm.saveNotice(
                        onSuccess = {
                            Log.d("writeArticle", "공지 등록 성공")
                            navController.popBackStack()   // 공지 목록으로 복귀
                        },
                        onError = { msg ->
                            Log.e("writeArticle", "공지 등록 실패: $msg")
                        }
                    )
                },
                enabled = vm.canSave() && !isLoading,
                modifier = BaseModifiers.BaseBtnModifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(if (isLoading) "저장 중..." else "글쓰기")
            }
        }
    }
}
