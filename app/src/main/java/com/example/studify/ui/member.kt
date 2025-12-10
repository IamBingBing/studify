package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun member(
    navController: NavController,
    vm: groupVM = hiltViewModel()
) {
    val users by vm.users
    val errorMessage by vm.errorMessage

    Scaffold(
        topBar = { groupNavigation(navController = navController) },
        bottomBar = { navigationbar(navController) }
    ) { padding ->

        when {

            !errorMessage.isNullOrEmpty() && users.isEmpty() -> Box(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text(errorMessage!!, color = MaterialTheme.colorScheme.error) }

            users.isEmpty() -> Box(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("멤버 정보가 없습니다.") }

            else -> LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(users) { user ->
                    val userId = user.userid
                    MemberRow(name = user.username ?: "이름 없음",
                        onClick = {
                            userId.let { id ->
                                navController.navigate("profilepage/$id")
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun MemberRow(
    name: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        color = Color(0xFFE0E8F5),
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
