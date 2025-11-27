package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun Guideline(
    vm: GuideLineVM = hiltViewModel(),
    navController: NavController
) {
    val isLoading by vm.isLoading
    val bookList = vm.bookList
    val goal = vm.groupGoal

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("추천 도서") }) },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->

        Column(
            modifier = BaseModifiers.BaseModifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = "🎯 목표: $goal", style = MaterialTheme.typography.titleMedium)
            Text(text = "이 목표에 딱 맞는 책을 AI가 골라봤어요!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)

            Spacer(Modifier.height(16.dp))

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(8.dp))
                    Text("AI 사서가 책을 찾고 있어요...", modifier = Modifier.padding(top = 60.dp))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(bookList) { book ->
                        Card(
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(text = book.title, style = MaterialTheme.typography.titleLarge)
                                Text(text = "저자: ${book.author}", style = MaterialTheme.typography.labelMedium)
                                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                                Text(text = book.description, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}